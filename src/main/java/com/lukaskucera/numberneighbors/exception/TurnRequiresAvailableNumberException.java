package com.lukaskucera.numberneighbors.exception;

public class TurnRequiresAvailableNumberException
  extends TurnRequiresNumberException {

  public TurnRequiresAvailableNumberException(int value) {
    super("turn requires available number, " + value + " is not");
  }
}
