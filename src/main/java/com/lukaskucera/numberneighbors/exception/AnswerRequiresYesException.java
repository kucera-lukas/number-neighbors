package com.lukaskucera.numberneighbors.exception;

import com.lukaskucera.numberneighbors.enums.AnwserType;

public class AnswerRequiresYesException extends RuntimeException {

  public AnswerRequiresYesException(Long responseId) {
    super(
      String.format(
        "answer to response %d is required to be %s",
        responseId,
        AnwserType.YES
      )
    );
  }
}
