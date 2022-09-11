package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.Game;
import com.lukaskucera.numberneighbors.entity.Player;
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

  private static final Logger logger = LoggerFactory.getLogger(
    GameServiceImpl.class
  );

  public static final int GAME_PLAYER_LIMIT = 2;

  private final GameRepository gameRepository;

  public GameServiceImpl(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  @Override
  public Game getGameById(Long id) {
    return gameRepository
      .findById(id)
      .orElseThrow(() -> new GameNotFoundException(id));
  }

  @Override
  public Game newGame(String hostName) {
    final Game game = new Game();

    game.addPlayer(new Player(hostName, game));

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
