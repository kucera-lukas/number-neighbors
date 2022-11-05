package com.lukaskucera.numberneighbors.exception;

public class NumberNotFoundException extends RuntimeException {

  public NumberNotFoundException(int value, Long playerId) {
    super(
      String.format(
        "Number with value %d not found for player %d",
        value,
        playerId
      )
    );
  }
}
