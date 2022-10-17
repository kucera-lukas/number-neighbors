package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.exception.GameNotFoundException;
import com.lukaskucera.numberneighbors.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

  public static final int GAME_PLAYER_LIMIT = 2;
  private static final Logger logger = LoggerFactory.getLogger(
    GameServiceImpl.class
  );
  private final GameRepository gameRepository;

  public GameServiceImpl(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  @Override
  public GameEntity getGameById(Long id) {
    return gameRepository
      .findById(id)
      .orElseThrow(() -> new GameNotFoundException(id));
  }

  @Override
  public GameEntity newGame(String hostName) {
    final GameEntity game = new GameEntity();

    game.addPlayer(new PlayerEntity(hostName, game));

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
}
