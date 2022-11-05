package com.lukaskucera.numberneighbors.exception;

public class GameMissingPlayersException extends RuntimeException {

  public GameMissingPlayersException(Long gameId) {
    super("game " + gameId + " is missing players");
  }
}
