package com.lukaskucera.numberneighbors.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lukaskucera.numberneighbors.enums.PlayerType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class PlayerEntity extends BaseEntity {

  @OneToMany(
    mappedBy = "player",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @JsonManagedReference
  private final Set<NumberEntity> numbers;

  @Column(name = "name", nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", updatable = false, nullable = false)
  private PlayerType type;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id", updatable = false, nullable = false)
  @JsonBackReference
  private GameEntity game;

  public PlayerEntity() {
    this.numbers = new HashSet<>();
  }

  public PlayerEntity(String name, GameEntity game) {
    setName(name);
    setType(game.getPlayers().isEmpty() ? PlayerType.HOST : PlayerType.GUEST);
    setGame(game);
    this.numbers = new HashSet<>();
  }

  public PlayerEntity(String name, GameEntity game, Set<NumberEntity> numbers) {
    setName(name);
    setType(game.getPlayers().isEmpty() ? PlayerType.HOST : PlayerType.GUEST);
    setGame(game);
    this.numbers = numbers;
  }

  public Set<NumberEntity> getNumbers() {
    return Set.copyOf(numbers);
  }

  public void addNumber(NumberEntity number) {
    numbers.add(number);
    number.setPlayer(this);
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

  public GameEntity getGame() {
    return game;
  }

  public void setGame(GameEntity game) {
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
