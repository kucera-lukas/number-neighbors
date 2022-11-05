package com.lukaskucera.numberneighbors.exception;

public class TurnNotFoundException extends RuntimeException {

  public TurnNotFoundException(Long id) {
    super("turn " + id + " does not exist");
  }
}
