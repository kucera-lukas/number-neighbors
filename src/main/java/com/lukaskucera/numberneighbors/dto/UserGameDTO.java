package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import java.util.Optional;

public record UserGameDTO(
  Long id,
  UserDTO user,
  Optional<PlayerDTO> opponent,
  boolean ready
) {
  public static UserGameDTO fromPlayer(PlayerEntity player) {
    final GameEntity game = player.getGame();

    return new UserGameDTO(
      game.getId(),
      UserDTO.fromPlayer(player),
      player.getOpponentOptional().map(PlayerDTO::fromPlayer),
      game.isReady()
    );
  }
}
