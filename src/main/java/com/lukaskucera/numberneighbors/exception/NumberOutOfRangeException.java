package com.lukaskucera.numberneighbors.exception;

import com.lukaskucera.numberneighbors.service.NumberServiceImpl;

public class NumberOutOfRangeException extends RuntimeException {

  public NumberOutOfRangeException(int number) {
    super(
      "number " +
      number +
      " is not in the range [" +
      NumberServiceImpl.MIN_NUMBER +
      ", " +
      NumberServiceImpl.MAX_NUMBER +
      "]"
    );
  }
}
