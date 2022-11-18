package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.PlayerDTO;
import com.lukaskucera.numberneighbors.dto.UserGameDTO;
import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.exception.GameNotFoundException;
import com.lukaskucera.numberneighbors.exception.GamePopulatedException;
import com.lukaskucera.numberneighbors.exception.PlayerNameAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.PlayerNotFoundException;
import com.lukaskucera.numberneighbors.repository.GameRepository;
import com.lukaskucera.numberneighbors.repository.PlayerRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

  private static final Logger logger = LoggerFactory.getLogger(
    PlayerServiceImpl.class
  );

  private final GameServiceImpl gameService;

  private final GameRepository gameRepository;
  private final PlayerRepository playerRepository;

  public PlayerServiceImpl(
    GameServiceImpl gameService,
    GameRepository gameRepository,
    PlayerRepository playerRepository
  ) {
    this.gameService = gameService;
    this.gameRepository = gameRepository;
    this.playerRepository = playerRepository;
  }

  @Override
  public PlayerDTO getPlayerById(AuthDTO auth, Long id) {
    checkPlayerAccess(auth, id);

    return getPlayerEntity(id)
      .map(PlayerDTO::fromPlayer)
      .orElseThrow(() -> new PlayerNotFoundException(id));
  }

  @Override
  public Set<PlayerDTO> getPlayersByGameId(AuthDTO auth, Long gameId) {
    gameService.checkGameAccess(auth, gameId);

    return playerRepository
      .findPlayerEntitiesByGameId(gameId)
      .stream()
      .map(PlayerDTO::fromPlayer)
      .collect(Collectors.toSet());
  }

  @Override
  public UserGameDTO getPlayerUserGameByGameId(AuthDTO auth, Long gameId) {
    gameService.checkGameAccess(auth, gameId);

    return getPlayerEntity(auth.playerId())
      .map(UserGameDTO::fromPlayer)
      .orElseThrow(() -> new PlayerNotFoundException(auth.playerId()));
  }

  @Override
  @Transactional
  public PlayerDTO newPlayer(Long gameId, String name) {
    final GameEntity game = gameRepository
      .findById(gameId)
      .orElseThrow(() -> new GameNotFoundException(gameId));

    checkGamePlayersState(name, game);

    final PlayerEntity player = createPlayer(name, game);

    gameService.sendPayloadToPlayers(player.getGame());

    return PlayerDTO.fromPlayer(player);
  }

  public void checkGamePlayersState(String name, GameEntity game) {
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
  }

  public PlayerEntity createPlayer(String name, GameEntity game) {
    final PlayerEntity player = new PlayerEntity(name, game);
    game.addPlayer(player);

    playerRepository.save(player);

    return player;
  }

  @Override
  public void deletePlayerById(AuthDTO auth, Long id) {
    checkPlayerAccess(auth, id);

    try {
      playerRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new PlayerNotFoundException(id);
    }
  }

  @Override
  public void checkPlayerAccess(AuthDTO auth, Long playerId) {
    if (!auth.playerId().equals(playerId)) {
      logger.info(
        "Authenticated to access the player {} not {}",
        auth.playerId(),
        playerId
      );

      throw new AccessDeniedException("Access denied to player " + playerId);
    }
  }

  private Optional<PlayerEntity> getPlayerEntity(Long id) {
    return playerRepository.findById(id);
  }
}
