package com.lukaskucera.numberneighbors.exception;

public class TurnNotCompletedException extends RuntimeException {

  public TurnNotCompletedException(Long turnId) {
    super(String.format("Turn %d is not yet completed", turnId));
  }
}
