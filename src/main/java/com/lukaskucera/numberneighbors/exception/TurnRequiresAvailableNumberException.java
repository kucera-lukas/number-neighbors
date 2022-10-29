package com.lukaskucera.numberneighbors.exception;

public class TurnRequiresAvailableNumberException
  extends TurnRequiresNumberException {

  public TurnRequiresAvailableNumberException(int value) {
    super(String.format("Turn requires available number, %d is not", value));
  }
}
