package com.lukaskucera.numberneighbors.exception;

public class NumberNotFoundException extends RuntimeException {

  public NumberNotFoundException(int value, Long playerId) {
    super("number with value " + value + " not found for player " + playerId);
  }
}
