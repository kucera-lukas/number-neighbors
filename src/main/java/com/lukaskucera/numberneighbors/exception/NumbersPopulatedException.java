package com.lukaskucera.numberneighbors.exception;

public class NumbersPopulatedException extends RuntimeException {

  public NumbersPopulatedException(Long id) {
    super("player " + id + " already has numbers populated");
  }
}
