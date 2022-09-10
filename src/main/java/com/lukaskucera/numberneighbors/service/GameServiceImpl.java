package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.Game;
import com.lukaskucera.numberneighbors.entity.Player;
import com.lukaskucera.numberneighbors.exception.GameNotFoundException;
import com.lukaskucera.numberneighbors.repository.GameRepository;
import com.lukaskucera.numberneighbors.repository.PlayerRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {
  public static final int GAME_PLAYER_LIMIT = 2;

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
}
