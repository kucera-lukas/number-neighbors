package com.lukaskucera.numberneighbors.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lukaskucera.numberneighbors.enums.NumberType;
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
  name = "numbers",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = { "player_id", "value" }),
    @UniqueConstraint(columnNames = { "player_id", "type" }),
  }
)
@SuppressWarnings("NullAway.Init")
public class NumberEntity extends BaseEntity {

  @Column(name = "value", nullable = false)
  private int value;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "type", updatable = false, nullable = false)
  private NumberType type;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "player_id", updatable = false, nullable = false)
  @JsonBackReference
  private PlayerEntity player;

  public NumberEntity() {}

  public NumberEntity(int value, NumberType type, PlayerEntity player) {
    setValue(value);
    setType(type);
    setPlayer(player);
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public NumberType getType() {
    return type;
  }

  public void setType(NumberType type) {
    this.type = type;
  }

  public PlayerEntity getPlayer() {
    return player;
  }

  public void setPlayer(PlayerEntity player) {
    this.player = player;
  }
}
