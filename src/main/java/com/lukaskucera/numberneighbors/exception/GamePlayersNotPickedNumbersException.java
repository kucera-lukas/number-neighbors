package com.lukaskucera.numberneighbors.exception;

public class GamePlayersNotPickedNumbersException extends RuntimeException {

  public GamePlayersNotPickedNumbersException(Long gameId) {
    super(
      String.format("Players of game %d haven't picked numbers yet", gameId)
    );
  }
}
