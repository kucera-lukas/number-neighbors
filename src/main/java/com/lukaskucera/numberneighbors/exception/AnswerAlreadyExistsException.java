package com.lukaskucera.numberneighbors.exception;

public class AnswerAlreadyExistsException extends RuntimeException {

  public AnswerAlreadyExistsException(Long responseId) {
    super("answer already exists for response " + responseId);
  }
}
