package com.lukaskucera.numberneighbors.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {

  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

  public void removePlayer(Player player) {
    players.remove(player);
    player.setGame(null);
  }
}
