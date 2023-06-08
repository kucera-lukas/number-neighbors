package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.TurnEntity;
import java.util.Optional;

public record TurnDTO(
  Long id,
  Long playerId,
  int value,
  Optional<ResponseDTO> response,
  boolean complete
) {
  public static TurnDTO fromTurn(TurnEntity turn) {
    return new TurnDTO(
      turn.getId(),
      turn.getPlayer().getId(),
      turn.getValue(),
      Optional.ofNullable(turn.getResponse()).map(ResponseDTO::fromResponse),
      turn.isComplete()
    );
  }
}
