package com.lukaskucera.numberneighbors.exception;

public class GameNotReadyException extends RuntimeException {

  public GameNotReadyException(Long gameId) {
    super("game " + gameId + " is not yet ready");
  }
}
