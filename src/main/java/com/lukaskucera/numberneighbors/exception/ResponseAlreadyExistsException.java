package com.lukaskucera.numberneighbors.exception;

public class ResponseAlreadyExistsException extends RuntimeException {

  public ResponseAlreadyExistsException(Long turnId) {
    super(String.format("Response already exists for turn %d", turnId));
  }
}
