package com.lukaskucera.numberneighbors.payload;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.enums.PlayerType;

public record OpponentPayload(
  Long id,
  String name,
  PlayerType type,
  boolean ready
) {
  public static OpponentPayload fromPlayer(PlayerEntity player) {
    return new OpponentPayload(
      player.getId(),
      player.getName(),
      player.getType(),
      player.isReady()
    );
  }
}
