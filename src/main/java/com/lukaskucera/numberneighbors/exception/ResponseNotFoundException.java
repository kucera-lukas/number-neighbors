package com.lukaskucera.numberneighbors.exception;

public class ResponseNotFoundException extends RuntimeException {

  public ResponseNotFoundException(Long id) {
    super(String.format("Response %d does not exist", id));
  }
}
