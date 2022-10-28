package com.lukaskucera.numberneighbors.exception;

public class GuestPlayerMissingException extends PlayerMissingException {

  public GuestPlayerMissingException(Long gameId) {
    super(String.format("Game %d does not have a guest player", gameId));
  }
}
