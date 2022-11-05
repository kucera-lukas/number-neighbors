package com.lukaskucera.numberneighbors.exception;

public class PlayerNameAlreadyExistsException extends RuntimeException {

  public PlayerNameAlreadyExistsException(Long gameId, String name) {
    super("game " + gameId + " already has a player named " + name);
  }
}
