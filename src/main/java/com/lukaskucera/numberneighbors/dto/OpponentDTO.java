package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.enums.PlayerType;

public record OpponentDTO(
  Long id,
  String name,
  PlayerType type,
  boolean ready
) {
  public static OpponentDTO fromPlayer(PlayerEntity player) {
    return new OpponentDTO(
      player.getId(),
      player.getName(),
      player.getType(),
      player.isReady()
    );
  }
}
