package com.lukaskucera.numberneighbors.exception;

public class NumbersPopulatedException extends RuntimeException {

  public NumbersPopulatedException(Long id) {
    super(String.format("Player %d already has numbers populated", id));
  }
}
