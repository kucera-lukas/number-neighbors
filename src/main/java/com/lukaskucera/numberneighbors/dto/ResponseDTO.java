package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import com.lukaskucera.numberneighbors.enums.ResponseType;
import java.util.Optional;

public record ResponseDTO(
  Long id,
  ResponseType type,
  Optional<AnswerDTO> answer
) {
  public static ResponseDTO fromResponse(ResponseEntity response) {
    return new ResponseDTO(
      response.getId(),
      response.getType(),
      Optional.ofNullable(response.getAnswer()).map(AnswerDTO::fromAnswer)
    );
  }
}
