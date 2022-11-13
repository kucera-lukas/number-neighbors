package com.lukaskucera.numberneighbors.payload;

import com.lukaskucera.numberneighbors.entity.TurnEntity;
import java.util.Optional;

public record TurnPayload(
  Long id,
  int value,
  Optional<ResponsePayload> response,
  boolean complete
) {
  public static TurnPayload fromTurn(TurnEntity turn) {
    return new TurnPayload(
      turn.getId(),
      turn.getValue(),
      Optional
        .ofNullable(turn.getResponse())
        .map(ResponsePayload::fromResponse),
      turn.isComplete()
    );
  }
}
