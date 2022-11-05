package com.lukaskucera.numberneighbors.exception;

public class HostPlayerMissingException extends PlayerMissingException {

  public HostPlayerMissingException(Long gameId) {
    super("game " + gameId + " does not have a host player");
  }
}
