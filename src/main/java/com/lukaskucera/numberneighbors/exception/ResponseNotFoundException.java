package com.lukaskucera.numberneighbors.exception;

public class ResponseNotFoundException extends RuntimeException {

  public ResponseNotFoundException(Long id) {
    super("response " + id + " does not exist");
  }
}
