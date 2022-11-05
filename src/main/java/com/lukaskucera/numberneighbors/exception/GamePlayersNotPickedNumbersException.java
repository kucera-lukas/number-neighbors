package com.lukaskucera.numberneighbors.exception;

public class GamePlayersNotPickedNumbersException extends RuntimeException {

  public GamePlayersNotPickedNumbersException(Long gameId) {
    super("players of game " + gameId + " haven't picked numbers yet");
  }
}
