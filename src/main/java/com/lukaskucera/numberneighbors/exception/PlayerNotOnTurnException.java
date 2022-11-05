package com.lukaskucera.numberneighbors.exception;

public class PlayerNotOnTurnException extends RuntimeException {

  public PlayerNotOnTurnException(Long id) {
    super("player " + id + " is not on turn");
  }
}
