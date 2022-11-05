package com.lukaskucera.numberneighbors.exception;

import com.lukaskucera.numberneighbors.service.NumberServiceImpl;

public class NumberOutOfRangeException extends RuntimeException {

  public NumberOutOfRangeException(int number) {
    super(
      String.format(
        "Number %d is not in the range [%d, %d]",
        number,
        NumberServiceImpl.MIN_NUMBER,
        NumberServiceImpl.MAX_NUMBER
      )
    );
  }
}
