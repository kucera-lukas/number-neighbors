package com.lukaskucera.numberneighbors.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
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
    this.turns = new ArrayList<>();
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
  public Optional<PlayerEntity> getPlayerByName(String name) {
    return getPlayer(player -> player.getName().equals(name));
  }

  private Optional<PlayerEntity> getPlayer(
    Predicate<? super PlayerEntity> playerFilter
  ) {
    return players.stream().filter(playerFilter).findFirst();
  }

  @JsonIgnore
  public Optional<PlayerEntity> getHost() {
    return getPlayer(PlayerEntity::isHost);
  }

  @JsonIgnore
  public Optional<PlayerEntity> getGuest() {
    return getPlayer(PlayerEntity::isGuest);
  }

  @JsonIgnore
  public boolean isReady() {
    return (
      players.size() == 2 && players.stream().allMatch(PlayerEntity::isReady)
    );
  }
}
