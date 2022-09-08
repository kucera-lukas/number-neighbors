package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.Game;
import com.lukaskucera.numberneighbors.entity.Player;
import com.lukaskucera.numberneighbors.exception.GameNotFoundException;
import com.lukaskucera.numberneighbors.exception.GamePopulatedException;
import com.lukaskucera.numberneighbors.exception.PlayerNameAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.PlayerNotFoundException;
import com.lukaskucera.numberneighbors.repository.GameRepository;
import com.lukaskucera.numberneighbors.repository.PlayerRepository;
import java.util.Set;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

  private final GameRepository gameRepository;

  private final PlayerRepository playerRepository;

  public PlayerServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository) {
    this.gameRepository = gameRepository;
    this.playerRepository = playerRepository;
  }

  @Override
  public Player getPlayerById(Long id) {
    return playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException(id));
  }

  @Override
  public Set<Player> getPlayersByGameId(Long gameId) {
    return gameRepository
        .findById(gameId)
        .orElseThrow(() -> new GameNotFoundException(gameId))
        .getPlayers();
  }

  @Override
  public Player newPlayer(Long gameId, String name) {
    final Game game =
        gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
    final Set<Player> players = game.getPlayers();

    if (!players.isEmpty() && players.stream().anyMatch(p -> p.getName().equals(name))) {
      throw new PlayerNameAlreadyExistsException(gameId, name);
    }

    if (players.size() >= 2) {
      throw new GamePopulatedException(gameId);
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
}
