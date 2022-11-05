package com.lukaskucera.numberneighbors.exception;

public class TurnNotFoundException extends RuntimeException {

  public TurnNotFoundException(Long id) {
    super(String.format("Turn %d does not exist", id));
  }
}
