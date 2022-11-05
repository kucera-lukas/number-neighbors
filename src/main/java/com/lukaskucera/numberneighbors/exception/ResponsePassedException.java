package com.lukaskucera.numberneighbors.exception;

public class ResponsePassedException extends RuntimeException {

  public ResponsePassedException(Long id) {
    super("response " + id + " was passed");
  }
}
