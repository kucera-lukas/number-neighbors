package com.lukaskucera.numberneighbors.service;

import static com.lukaskucera.numberneighbors.service.GameServiceImpl.GAME_PLAYER_LIMIT;

import com.lukaskucera.numberneighbors.entity.Game;
import com.lukaskucera.numberneighbors.entity.Player;
import com.lukaskucera.numberneighbors.exception.GameNotFoundException;
import com.lukaskucera.numberneighbors.exception.GamePopulatedException;
import com.lukaskucera.numberneighbors.exception.PlayerNameAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.PlayerNotFoundException;
import com.lukaskucera.numberneighbors.repository.GameRepository;
import com.lukaskucera.numberneighbors.repository.PlayerRepository;
import java.util.Set;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

  private static final Logger logger = LoggerFactory.getLogger(
    PlayerServiceImpl.class
  );

  private final GameRepository gameRepository;

  private final PlayerRepository playerRepository;

  public PlayerServiceImpl(
    GameRepository gameRepository,
    PlayerRepository playerRepository
  ) {
    this.gameRepository = gameRepository;
    this.playerRepository = playerRepository;
  }

  @Override
  public Player getPlayerById(Long id) {
    return playerRepository
      .findById(id)
      .orElseThrow(() -> new PlayerNotFoundException(id));
  }

  @Override
  public Set<Player> getPlayersByGameId(Long gameId) {
    return gameRepository
      .findById(gameId)
      .orElseThrow(() -> new GameNotFoundException(gameId))
      .getPlayers();
  }

  @Override
  @Transactional
  public Player newPlayer(Long gameId, String name) {
    final Game game = gameRepository
      .findById(gameId)
      .orElseThrow(() -> new GameNotFoundException(gameId));
    final Set<Player> players = game.getPlayers();

    if (players.size() >= GAME_PLAYER_LIMIT) {
      logger.info(
        "Can't add player \"{}\" to the game {} as it's already populated",
        name,
        gameId
      );
      throw new GamePopulatedException(gameId);
    }

    if (
      !players.isEmpty() &&
      players.stream().anyMatch(p -> p.getName().equals(name))
    ) {
      logger.info(
        "Player named \"{}\" already exists in the game {}",
        name,
        gameId
      );
      throw new PlayerNameAlreadyExistsException(gameId, name);
    }

    final Player player = new Player(name, game);
    game.addPlayer(player);

    playerRepository.save(player);

    return player;
  }

  @Override
  public void deletePlayerById(Long id) {
    try {
      playerRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new PlayerNotFoundException(id);
    }
  }

  @Override
  public void checkPlayerAccess(
    Long playerId,
    JwtAuthenticationToken jwtToken
  ) {
    final Long claimedPlayerId = (Long) jwtToken
      .getToken()
      .getClaims()
      .get("playerId");

    if (claimedPlayerId == null || !claimedPlayerId.equals(playerId)) {
      logger.info(
        "Authenticated to access the player {} not {}",
        claimedPlayerId,
        playerId
      );
      throw new AccessDeniedException("Access denied to player " + playerId);
    }
  }
}
