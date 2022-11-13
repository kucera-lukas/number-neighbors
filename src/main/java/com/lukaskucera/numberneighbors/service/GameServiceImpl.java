package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.exception.GameMissingPlayersException;
import com.lukaskucera.numberneighbors.exception.GameNotFoundException;
import com.lukaskucera.numberneighbors.exception.GamePlayersNotPickedNumbersException;
import com.lukaskucera.numberneighbors.payload.GamePayload;
import com.lukaskucera.numberneighbors.repository.GameRepository;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
  public GameEntity getGameById(Long id) {
    return gameRepository
      .findById(id)
      .orElseThrow(() -> new GameNotFoundException(id));
  }

  @Override
  public GameEntity newGame() {
    final GameEntity game = new GameEntity();

    gameRepository.save(game);

    return game;
  }

  @Override
  public void deleteGameById(Long id) {
    try {
      gameRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new GameNotFoundException(id);
    }
  }

  @Override
  public void checkGameAccess(Long gameId, JwtAuthenticationToken jwtToken) {
    final Long claimedGameId = (Long) jwtToken
      .getToken()
      .getClaims()
      .get("gameId");

    if (claimedGameId == null || !claimedGameId.equals(gameId)) {
      logger.info(
        "Authenticated to access the game {} not {}",
        claimedGameId,
        gameId
      );
      throw new AccessDeniedException("Access denied to game " + gameId);
    }
  }

  @Override
  public void checkGameReady(GameEntity game) {
    final Set<PlayerEntity> players = game.getPlayers();

    if (players.size() != PLAYER_COUNT) {
      logger.info(
        "Game {} has {} player(s), {} is needed",
        game.getId(),
        players.size(),
        PLAYER_COUNT
      );

      throw new GameMissingPlayersException(game.getId());
    }

    if (
      players
        .stream()
        .anyMatch(player ->
          player.getNumbers().size() !=
          NumberServiceImpl.NUMBER_COUNT_PER_PLAYER
        )
    ) {
      throw new GamePlayersNotPickedNumbersException(game.getId());
    }
  }

  @Override
  public void sendPayloadToPlayers(GameEntity game) {
    for (PlayerEntity player : game.getPlayers()) {
      logger.debug(
        "Sending game {} payload to player {}",
        game.getId(),
        player.getId()
      );

      simpMessagingTemplate.convertAndSendToUser(
        player.getSub(),
        "/queue/payload",
        GamePayload.fromPlayer(player)
      );
    }
  }
}
