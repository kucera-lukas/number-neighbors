package com.lukaskucera.numberneighbors.exception;

public class PlayerNotOnTurnException extends RuntimeException {

  public PlayerNotOnTurnException(Long id) {
    super(String.format("Player %d is not on turn", id));
  }
}
