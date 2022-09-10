package com.lukaskucera.numberneighbors.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lukaskucera.numberneighbors.exception.GuestPlayerMissingException;
import com.lukaskucera.numberneighbors.exception.HostPlayerMissingException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {

  @OneToMany(
    mappedBy = "game",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @JsonManagedReference
  private final Set<Player> players;

  public Game() {
    this.players = new HashSet<>();
  }

  public Set<Player> getPlayers() {
    return players;
  }

  public void addPlayer(Player player) {
    players.add(player);
    player.setGame(this);
  }

  @JsonIgnore
  public Player getHost() {
    return getPlayerByType(
      Player::isHost,
      () -> new HostPlayerMissingException(getId())
    );
  }

  private <X extends Throwable> Player getPlayerByType(
    Predicate<? super Player> playerFilter,
    Supplier<? extends X> exceptionSupplier
  ) throws X {
    return players
      .stream()
      .filter(playerFilter)
      .findFirst()
      .orElseThrow(exceptionSupplier);
  }

  @JsonIgnore
  public Player getGuest() {
    return getPlayerByType(
      Player::isGuest,
      () -> new GuestPlayerMissingException(getId())
    );
  }
}
