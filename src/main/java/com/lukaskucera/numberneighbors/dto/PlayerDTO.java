package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.enums.PlayerType;
import java.util.List;

public record PlayerDTO(
  Long id,
  String name,
  PlayerType type,
  List<NumberDTO> numbers,
  boolean ready
) {
  public static PlayerDTO fromPlayer(PlayerEntity player) {
    return new PlayerDTO(
      player.getId(),
      player.getName(),
      player.getType(),
      player.getNumbers().stream().map(NumberDTO::fromNumber).toList(),
      player.isReady()
    );
  }
}
