package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.enums.PlayerType;

public record PlayerDTO(
  Long id,
  Long gameId,
  String name,
  PlayerType type,
  boolean ready
) {
  public static PlayerDTO fromPlayer(PlayerEntity player) {
    return new PlayerDTO(
      player.getId(),
      player.getGame().getId(),
      player.getName(),
      player.getType(),
      player.isReady()
    );
  }
}
