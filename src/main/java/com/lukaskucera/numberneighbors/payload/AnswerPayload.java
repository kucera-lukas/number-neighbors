package com.lukaskucera.numberneighbors.payload;

import com.lukaskucera.numberneighbors.entity.AnswerEntity;
import com.lukaskucera.numberneighbors.enums.AnwserType;

public record AnswerPayload(Long id, AnwserType type) {
  public static AnswerPayload fromAnswer(AnswerEntity answer) {
    return new AnswerPayload(answer.getId(), answer.getType());
  }
}
