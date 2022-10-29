package com.lukaskucera.numberneighbors.exception;

public class TurnRequiresChosenNumberException
  extends TurnRequiresNumberException {

  public TurnRequiresChosenNumberException(int value) {
    super(String.format("Turn requires a chosen number, %d is not", value));
  }
}
