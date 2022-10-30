package com.lukaskucera.numberneighbors.exception;

import java.util.Arrays;

public class NumbersNotDistinctException extends RuntimeException {

  public NumbersNotDistinctException(int... numbers) {
    super(
      String.format("Numbers %s are not distinct", Arrays.toString(numbers))
    );
  }
}
