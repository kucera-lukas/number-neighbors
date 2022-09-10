package com.lukaskucera.numberneighbors.dto;

import com.lukaskucera.numberneighbors.enums.PlayerType;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/** A DTO for the {@link com.lukaskucera.numberneighbors.entity.Player} entity */
public record PlayerDto(
  Long id,
  Date created,
  Date modified,
  String name,
  PlayerType type,
  GameDto game
)
  implements Serializable {
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlayerDto entity = (PlayerDto) o;
    return (
      Objects.equals(this.id, entity.id) &&
      Objects.equals(this.created, entity.created) &&
      Objects.equals(this.modified, entity.modified) &&
      Objects.equals(this.name, entity.name) &&
      Objects.equals(this.type, entity.type) &&
      Objects.equals(this.game, entity.game)
    );
  }

  @Override
  public @NotNull String toString() {
    return (
      getClass().getSimpleName() +
      "(" +
      "id = " +
      id +
      ", " +
      "created = " +
      created +
      ", " +
      "modified = " +
      modified +
      ", " +
      "name = " +
      name +
      ", " +
      "type = " +
      type +
      ", " +
      "game = " +
      game +
      ")"
    );
  }
}
