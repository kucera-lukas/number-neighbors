package com.lukaskucera.numberneighbors.exception;

public class PlayerNumbersPopulatedException extends RuntimeException {

  public PlayerNumbersPopulatedException(Long id) {
    super(String.format("Player %d has already picked numbers", id));
  }
}
