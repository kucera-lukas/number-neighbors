package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import java.util.Optional;

public record GameDTO(
  Long id,
  Optional<PlayerDTO> player,
  Optional<OpponentDTO> opponent,
  boolean ready
) {
  public static GameDTO fromPlayer(PlayerEntity player) {
    final GameEntity game = player.getGame();

    return new GameDTO(
      game.getId(),
      Optional.of(PlayerDTO.fromPlayer(player)),
      player.getOpponentOptional().map(OpponentDTO::fromPlayer),
      game.isReady()
    );
  }
}
