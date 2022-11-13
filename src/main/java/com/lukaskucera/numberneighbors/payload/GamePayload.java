package com.lukaskucera.numberneighbors.payload;

import com.lukaskucera.numberneighbors.entity.GameEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import java.util.Optional;

public record GamePayload(
  Long id,
  Optional<PlayerPayload> player,
  Optional<OpponentPayload> opponent,
  boolean ready
) {
  public static GamePayload fromPlayer(PlayerEntity player) {
    final GameEntity game = player.getGame();

    return new GamePayload(
      game.getId(),
      Optional.of(PlayerPayload.fromPlayer(player)),
      player.getOpponentOptional().map(OpponentPayload::fromPlayer),
      game.isReady()
    );
  }
}
