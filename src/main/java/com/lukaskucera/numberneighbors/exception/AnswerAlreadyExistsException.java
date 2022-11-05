package com.lukaskucera.numberneighbors.exception;

public class AnswerAlreadyExistsException extends RuntimeException {

  public AnswerAlreadyExistsException(Long responseId) {
    super(String.format("Answer already exists for response %d", responseId));
  }
}
