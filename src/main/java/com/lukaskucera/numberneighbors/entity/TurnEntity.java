package com.lukaskucera.numberneighbors.entity;

import com.lukaskucera.numberneighbors.enums.ResponseType;
import com.lukaskucera.numberneighbors.service.NumberServiceImpl;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "turns")
@SuppressWarnings("NullAway.Init")
public class TurnEntity extends BaseEntity {

  @Column(name = "value", updatable = false, nullable = false)
  @Range(min = NumberServiceImpl.MIN_NUMBER, max = NumberServiceImpl.MAX_NUMBER)
  private int value;

  @OneToOne(mappedBy = "turn")
  @Nullable
  private ResponseEntity response;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id", updatable = false, nullable = false)
  private GameEntity game;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "player_id", updatable = false, nullable = false)
  private PlayerEntity player;

  public TurnEntity() {}

  public TurnEntity(int value, PlayerEntity player) {
    setValue(value);
    setPlayer(player);
    setGame(player.getGame());
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @Nullable
  public ResponseEntity getResponse() {
    return response;
  }

  public void setResponse(@Nullable ResponseEntity response) {
    this.response = response;

    if (response != null) {
      response.setTurn(this);
    }
  }

  public GameEntity getGame() {
    return game;
  }

  public void setGame(GameEntity game) {
    this.game = game;
  }

  public PlayerEntity getPlayer() {
    return player;
  }

  public void setPlayer(PlayerEntity player) {
    this.player = player;
  }

  public boolean needsResponse() {
    return response == null;
  }

  public boolean isComplete() {
    if (response == null) {
      return false;
    }

    if (response.getType() == ResponseType.PASS) {
      return true;
    }

    return response.getAnswer() != null;
  }
}
