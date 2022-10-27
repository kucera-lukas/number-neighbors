package com.lukaskucera.numberneighbors.exception;

public class HostPlayerMissingException extends PlayerMissingException {

  public HostPlayerMissingException(Long gameId) {
    super(String.format("Game %d does not have a host player", gameId));
  }
}
