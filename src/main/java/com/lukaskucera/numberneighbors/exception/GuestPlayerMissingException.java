package com.lukaskucera.numberneighbors.exception;

public class GuestPlayerMissingException extends RuntimeException {

  public GuestPlayerMissingException(Long gameId) {
    super(String.format("Game %d does not have a guest player", gameId));
  }
}
