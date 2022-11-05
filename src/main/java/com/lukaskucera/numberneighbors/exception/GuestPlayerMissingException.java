package com.lukaskucera.numberneighbors.exception;

public class GuestPlayerMissingException extends PlayerMissingException {

  public GuestPlayerMissingException(Long gameId) {
    super("game " + gameId + " does not have a guest player");
  }
}
