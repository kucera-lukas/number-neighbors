package com.lukaskucera.numberneighbors.exception;

public class NumbersNotDistinctException extends RuntimeException {

  public NumbersNotDistinctException(int first, int second, int third) {
    super(
      String.format(
        "Number [%d, %d, %d] are not distinct",
        first,
        second,
        third
      )
    );
  }
}
