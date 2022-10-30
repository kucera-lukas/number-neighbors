package com.lukaskucera.numberneighbors.exception;

public class GameMissingPlayersException extends RuntimeException {

  public GameMissingPlayersException(Long gameId) {
    super(String.format("Game %d is missing players", gameId));
  }
}
