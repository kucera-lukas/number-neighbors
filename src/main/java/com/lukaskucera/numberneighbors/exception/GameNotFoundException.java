package com.lukaskucera.numberneighbors.exception;

public class GameNotFoundException extends RuntimeException {

  public GameNotFoundException(Long id) {
    super("game " + id + " does not exist");
  }
}
