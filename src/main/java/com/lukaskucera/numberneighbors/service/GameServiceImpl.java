package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.GameDTO;
import com.lukaskucera.numberneighbors.dto.UserGameDTO;
import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.exception.GameNotFoundException;
import com.lukaskucera.numberneighbors.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

  public static final int PLAYER_COUNT = 2;
  private static final Logger logger = LoggerFactory.getLogger(
    GameServiceImpl.class
  );
  private final GameRepository gameRepository;

  private final SimpMessagingTemplate simpMessagingTemplate;

  public GameServiceImpl(
    GameRepository gameRepository,
    SimpMessagingTemplate simpMessagingTemplate
  ) {
    this.gameRepository = gameRepository;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @Override
  public GameDTO getGameById(AuthDTO auth, Long id) {
    checkGameAccess(auth, id);

    return gameRepository
      .findById(id)
      .map(GameDTO::fromGame)
      .orElseThrow(() -> new GameNotFoundException(id));
  }

  @Override
  public GameDTO newGame() {
    final GameEntity game = new GameEntity();

    gameRepository.save(game);

    return GameDTO.fromGame(game);
  }

  @Override
  public void checkGameAccess(AuthDTO auth, Long gameId) {
    if (!auth.gameId().equals(gameId)) {
      logger.info(
        "Authenticated to access the game {} not {}",
        auth.gameId(),
        gameId
      );

      throw new AccessDeniedException("Access denied to game " + gameId);
    }
  }

  @Override
  public void sendPayloadToPlayers(GameEntity game) {
    for (PlayerEntity player : game.getPlayers()) {
      logger.info(
        "Sending game {} payload to player {}",
        game.getId(),
        player.getId()
      );

      simpMessagingTemplate.convertAndSendToUser(
        player.getSub(),
        "/queue/payload",
        UserGameDTO.fromPlayer(player)
      );
    }
  }
}
