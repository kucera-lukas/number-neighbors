package com.lukaskucera.numberneighbors.payload;

import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import com.lukaskucera.numberneighbors.enums.ResponseType;
import java.util.Optional;

public record ResponsePayload(
  Long id,
  ResponseType type,
  Optional<AnswerPayload> answer
) {
  public static ResponsePayload fromResponse(ResponseEntity response) {
    return new ResponsePayload(
      response.getId(),
      response.getType(),
      Optional.ofNullable(response.getAnswer()).map(AnswerPayload::fromAnswer)
    );
  }
}
