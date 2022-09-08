package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.Game;
import com.lukaskucera.numberneighbors.entity.Player;
import com.lukaskucera.numberneighbors.exception.GameNotFoundException;
import com.lukaskucera.numberneighbors.exception.GamePopulatedException;
import com.lukaskucera.numberneighbors.exception.PlayerNameAlreadyExistsException;
import com.lukaskucera.numberneighbors.repository.GameRepository;
import com.lukaskucera.numberneighbors.repository.PlayerRepository;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
  private final GameRepository gameRepository;

  private final PlayerRepository playerRepository;

  public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository) {
    this.gameRepository = gameRepository;
    this.playerRepository = playerRepository;
  }

  @Override
  public Game getGameById(Long id) {
    return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
  }

  @Override
  public Game newGameWithPlayer(String name) {
    final Game game = new Game();

    final Player player = new Player(name, game);
    game.addPlayer(player);

    gameRepository.save(game);

    return game;
  }

  @Override
  public Game updateGameWithPlayer(Long id, String name) {
    final Game game = getGameById(id);
    final Set<Player> players = game.getPlayers();

    if (!players.isEmpty() && players.stream().anyMatch(p -> p.getName().equals(name))) {
      throw new PlayerNameAlreadyExistsException(id, name);
    }

    if (players.size() >= 2) {
      throw new GamePopulatedException(id);
    }

    final Player player = new Player(name, game);
    game.addPlayer(player);

    playerRepository.save(player);

    return game;
  }
}
