package com.lukaskucera.numberneighbors.exception;

import com.lukaskucera.numberneighbors.enums.ResponseType;

public class ResponsePassRequiredException extends RuntimeException {

  public ResponsePassRequiredException(Long turnId) {
    super(
      "response to turn " + turnId + " is required to be " + ResponseType.PASS
    );
  }
}
