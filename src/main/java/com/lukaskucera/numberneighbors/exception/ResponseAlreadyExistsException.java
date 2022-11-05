package com.lukaskucera.numberneighbors.exception;

public class ResponseAlreadyExistsException extends RuntimeException {

  public ResponseAlreadyExistsException(Long turnId) {
    super("response already exists for turn " + turnId);
  }
}
