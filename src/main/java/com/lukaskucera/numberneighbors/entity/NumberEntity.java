package com.lukaskucera.numberneighbors.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lukaskucera.numberneighbors.enums.NumberType;
import com.lukaskucera.numberneighbors.service.NumberServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.validator.constraints.Range;

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
  @Range(min = NumberServiceImpl.MIN_NUMBER, max = NumberServiceImpl.MAX_NUMBER)
  private int value;

  @Column(
    name = "is_guessed",
    nullable = false,
    columnDefinition = "BOOLEAN DEFAULT false"
  )
  private boolean isGuessed = false;

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

  public boolean getIsGuessed() {
    return isGuessed;
  }

  public void setIsGuessed(boolean isGuessed) {
    this.isGuessed = isGuessed;
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

  @JsonIgnore
  public List<Integer> getAvailableNumbers() {
    if (isGuessed) {
      return List.of();
    }

    final ArrayList<Integer> result = new ArrayList<>();

    if (value != NumberServiceImpl.MIN_NUMBER) {
      result.add(value - 1);
    }

    result.add(value);

    if (value != NumberServiceImpl.MAX_NUMBER) {
      result.add(value + 1);
    }

    return result;
  }
}
