package com.lukaskucera.numberneighbors.exception;

public class TurnNotCompletedException extends RuntimeException {

  public TurnNotCompletedException(Long turnId) {
    super("turn " + turnId + " is not yet completed");
  }
}
