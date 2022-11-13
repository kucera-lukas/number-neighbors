package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.exception.GamePopulatedException;
import com.lukaskucera.numberneighbors.exception.PlayerIdMissingInJwtTokenClaimsException;
import com.lukaskucera.numberneighbors.exception.PlayerNameAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.PlayerNotFoundException;
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

  private final PlayerRepository playerRepository;

  public PlayerServiceImpl(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @Override
  public Long getPlayerIdFromToken(JwtAuthenticationToken jwtToken) {
    final Long playerId = (Long) jwtToken
      .getToken()
      .getClaims()
      .get("playerId");

    if (playerId == null) {
      throw new PlayerIdMissingInJwtTokenClaimsException(jwtToken);
    }

    return playerId;
  }

  @Override
  public PlayerEntity getPlayerById(Long id) {
    return playerRepository
      .findById(id)
      .orElseThrow(() -> new PlayerNotFoundException(id));
  }

  @Override
  public PlayerEntity getPlayerByJwtToken(JwtAuthenticationToken jwtToken) {
    final Long playerId = getPlayerIdFromToken(jwtToken);

    return getPlayerById(playerId);
  }

  @Override
  public Set<PlayerEntity> getPlayersByGameId(Long gameId) {
    return playerRepository.findPlayerEntitiesByGameId(gameId);
  }

  @Override
  @Transactional
  public PlayerEntity newPlayer(String name, GameEntity game) {
    final Set<PlayerEntity> players = game.getPlayers();

    if (players.size() >= GameServiceImpl.PLAYER_COUNT) {
      logger.info(
        "Can't add player \"{}\" to the game {} as it's already populated",
        name,
        game.getId()
      );
      throw new GamePopulatedException(game.getId());
    }

    if (
      !players.isEmpty() &&
      players.stream().anyMatch(p -> p.getName().equals(name))
    ) {
      logger.info(
        "Player named \"{}\" already exists in the game {}",
        name,
        game.getId()
      );
      throw new PlayerNameAlreadyExistsException(game.getId(), name);
    }

    final PlayerEntity player = new PlayerEntity(name, game);
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
    final Long claimedPlayerId = getPlayerIdFromToken(jwtToken);

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
