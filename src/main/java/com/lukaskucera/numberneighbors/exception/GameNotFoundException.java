package com.lukaskucera.numberneighbors.exception;

public class GameNotFoundException extends RuntimeException {

  public GameNotFoundException(Long id) {
    super(String.format("Game %d does not exist", id));
  }
}
