package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.AnswerEntity;
import com.lukaskucera.numberneighbors.enums.AnwserType;

public record AnswerDTO(Long id, AnwserType type) {
  public static AnswerDTO fromAnswer(AnswerEntity answer) {
    return new AnswerDTO(answer.getId(), answer.getType());
  }
}
