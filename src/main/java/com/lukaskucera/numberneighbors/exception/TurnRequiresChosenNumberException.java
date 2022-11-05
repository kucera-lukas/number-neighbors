package com.lukaskucera.numberneighbors.exception;

public class TurnRequiresChosenNumberException
  extends TurnRequiresNumberException {

  public TurnRequiresChosenNumberException(int value) {
    super("turn requires a chosen number, " + value + " is not");
  }
}
