package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.enums.PlayerType;
import java.util.List;

public record PlayerDTO(
  Long id,
  Long gameId,
  String name,
  PlayerType type,
  List<NumberDTO> numbers,
  boolean ready
) {
  public static PlayerDTO fromPlayer(PlayerEntity player) {
    return new PlayerDTO(
      player.getId(),
      player.getGame().getId(),
      player.getName(),
      player.getType(),
      player
        .getNumbers()
        .stream()
        .filter(NumberEntity::getIsGuessed)
        .map(NumberDTO::fromNumber)
        .toList(),
      player.isReady()
    );
  }
}
