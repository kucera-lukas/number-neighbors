package com.lukaskucera.numberneighbors.payload;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.enums.PlayerType;
import java.util.List;

public record PlayerPayload(
  Long id,
  String name,
  PlayerType type,
  List<NumberPayload> numbers,
  boolean ready
) {
  public static PlayerPayload fromPlayer(PlayerEntity player) {
    return new PlayerPayload(
      player.getId(),
      player.getName(),
      player.getType(),
      player.getNumbers().stream().map(NumberPayload::fromNumber).toList(),
      player.isReady()
    );
  }
}
