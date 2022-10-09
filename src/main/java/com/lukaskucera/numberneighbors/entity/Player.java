package com.lukaskucera.numberneighbors.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lukaskucera.numberneighbors.enums.PlayerType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
  name = "players",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = { "game_id", "name" }),
    @UniqueConstraint(columnNames = { "game_id", "type" }),
  }
)
@SuppressWarnings("NullAway.Init")
public class Player extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", updatable = false, nullable = false)
  private PlayerType type;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id", updatable = false, nullable = false)
  @JsonBackReference
  private Game game;

  public Player() {}

  public Player(String name, Game game) {
    setName(name);
    setType(game.getPlayers().isEmpty() ? PlayerType.HOST : PlayerType.GUEST);
    setGame(game);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PlayerType getType() {
    return type;
  }

  public void setType(PlayerType type) {
    this.type = type;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  @JsonIgnore
  public String getSub() {
    return getId().toString();
  }

  @JsonIgnore
  public Boolean isHost() {
    return type == PlayerType.HOST;
  }

  @JsonIgnore
  public Boolean isGuest() {
    return type == PlayerType.GUEST;
  }
}
