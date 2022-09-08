package com.lukaskucera.numberneighbors.exception;

public class PlayerNameAlreadyExistsException extends RuntimeException {
  public PlayerNameAlreadyExistsException(Long gameId, String name) {
    super(String.format("Game %d already has a player named %s", gameId, name));
  }
}
