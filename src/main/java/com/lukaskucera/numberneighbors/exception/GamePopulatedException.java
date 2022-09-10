package com.lukaskucera.numberneighbors.exception;

public class GamePopulatedException extends RuntimeException {

  public GamePopulatedException(Long id) {
    super(String.format("Game %d already has 2 players", id));
  }
}
