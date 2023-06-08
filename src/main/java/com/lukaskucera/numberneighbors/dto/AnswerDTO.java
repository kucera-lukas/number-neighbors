package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.AnswerEntity;
import com.lukaskucera.numberneighbors.enums.AnswerType;

public record AnswerDTO(Long id, Long playerId, AnswerType type) {
  public static AnswerDTO fromAnswer(AnswerEntity answer) {
    return new AnswerDTO(
      answer.getId(),
      answer.getPlayer().getId(),
      answer.getType()
    );
  }
}
