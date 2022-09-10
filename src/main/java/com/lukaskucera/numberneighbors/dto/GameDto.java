package com.lukaskucera.numberneighbors.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/** A DTO for the {@link com.lukaskucera.numberneighbors.entity.Game} entity */
public record GameDto(Long id, Date created, Date modified)
  implements Serializable {
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GameDto entity = (GameDto) o;
    return (
      Objects.equals(this.id, entity.id) &&
      Objects.equals(this.created, entity.created) &&
      Objects.equals(this.modified, entity.modified)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, created, modified);
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
      ")"
    );
  }
}
