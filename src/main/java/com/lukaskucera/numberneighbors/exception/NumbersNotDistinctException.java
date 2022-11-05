package com.lukaskucera.numberneighbors.exception;

import java.util.Arrays;

public class NumbersNotDistinctException extends RuntimeException {

  public NumbersNotDistinctException(int... numbers) {
    super("numbers " + Arrays.toString(numbers) + " are not distinct");
  }
}
