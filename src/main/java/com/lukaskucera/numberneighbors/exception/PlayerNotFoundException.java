package com.lukaskucera.numberneighbors.exception;

public class PlayerNotFoundException extends RuntimeException {

  public PlayerNotFoundException(Long id) {
    super(String.format("Player %d does not exist", id));
  }

  public PlayerNotFoundException(String name) {
    super(String.format("Player \"%s\" does not exist", name));
  }
}
