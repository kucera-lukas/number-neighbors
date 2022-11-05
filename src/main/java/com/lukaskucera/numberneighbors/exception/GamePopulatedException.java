package com.lukaskucera.numberneighbors.exception;

public class GamePopulatedException extends RuntimeException {

  public GamePopulatedException(Long id) {
    super("game " + id + " already has 2 players");
  }
}
