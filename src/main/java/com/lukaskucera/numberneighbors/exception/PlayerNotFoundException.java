package com.lukaskucera.numberneighbors.exception;

public class PlayerNotFoundException extends RuntimeException {

  public PlayerNotFoundException(Long id) {
    super("player " + id + " does not exist");
  }

  public PlayerNotFoundException(String name) {
    super("Player \"" + name + "\" does not exist");
  }
}
