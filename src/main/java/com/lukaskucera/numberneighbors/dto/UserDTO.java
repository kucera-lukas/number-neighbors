package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.enums.PlayerType;
import java.util.List;

public record UserDTO(
  Long id,
  Long gameId,
  String name,
  PlayerType type,
  List<NumberDTO> numbers,
  boolean ready
) {
  public static UserDTO fromPlayer(PlayerEntity player) {
    return new UserDTO(
      player.getId(),
      player.getGame().getId(),
      player.getName(),
      player.getType(),
      player.getNumbers().stream().map(NumberDTO::fromNumber).toList(),
      player.isReady()
    );
  }
}
