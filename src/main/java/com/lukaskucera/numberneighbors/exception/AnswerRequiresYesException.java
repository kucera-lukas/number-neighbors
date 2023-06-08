package com.lukaskucera.numberneighbors.exception;

import com.lukaskucera.numberneighbors.enums.AnswerType;

public class AnswerRequiresYesException extends RuntimeException {

  public AnswerRequiresYesException(Long responseId) {
    super(
      "answer to response " +
      responseId +
      " is required to be " +
      AnswerType.YES
    );
  }
}
