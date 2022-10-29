package com.lukaskucera.numberneighbors.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lukaskucera.numberneighbors.exception.GuestPlayerMissingException;
import com.lukaskucera.numberneighbors.exception.HostPlayerMissingException;
import com.lukaskucera.numberneighbors.exception.PlayerNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "games")
public class GameEntity extends BaseEntity {

  @OneToMany(
    mappedBy = "game",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @JsonManagedReference
  private final Set<PlayerEntity> players;

  @OneToMany(
    mappedBy = "game",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @OrderBy(clause = "created ASC")
  @JsonManagedReference
  private final List<TurnEntity> turns;

  public GameEntity() {
    this.players = new HashSet<>();
    this.turns = List.of();
  }

  public Set<PlayerEntity> getPlayers() {
    return Set.copyOf(players);
  }

  public void addPlayer(PlayerEntity player) {
    players.add(player);
    player.setGame(this);
  }

  public List<TurnEntity> getTurns() {
    return List.copyOf(turns);
  }

  public void addTurn(TurnEntity turn) {
    turns.add(turn);
    turn.setGame(this);
  }

  @JsonIgnore
  public PlayerEntity getPlayerByName(String name) {
    return getPlayer(
      player -> player.getName().equals(name),
      () -> new PlayerNotFoundException(name)
    );
  }

  private <X extends Throwable> PlayerEntity getPlayer(
    Predicate<? super PlayerEntity> playerFilter,
    Supplier<? extends X> exceptionSupplier
  ) throws X {
    return players
      .stream()
      .filter(playerFilter)
      .findFirst()
      .orElseThrow(exceptionSupplier);
  }

  @JsonIgnore
  public PlayerEntity getHost() {
    return getPlayer(
      PlayerEntity::isHost,
      () -> new HostPlayerMissingException(getId())
    );
  }

  @JsonIgnore
  public PlayerEntity getGuest() {
    return getPlayer(
      PlayerEntity::isGuest,
      () -> new GuestPlayerMissingException(getId())
    );
  }
}
