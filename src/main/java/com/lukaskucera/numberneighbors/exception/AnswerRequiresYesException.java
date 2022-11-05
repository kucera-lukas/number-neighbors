package com.lukaskucera.numberneighbors.exception;

import com.lukaskucera.numberneighbors.enums.AnwserType;

public class AnswerRequiresYesException extends RuntimeException {

  public AnswerRequiresYesException(Long responseId) {
    super(
      "answer to response " +
      responseId +
      " is required to be " +
      AnwserType.YES
    );
  }
}
