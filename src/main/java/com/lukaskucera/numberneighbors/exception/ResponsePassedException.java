package com.lukaskucera.numberneighbors.exception;

public class ResponsePassedException extends RuntimeException {

  public ResponsePassedException(Long id) {
    super(String.format("Response %d was passed", id));
  }
}
