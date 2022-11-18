package com.lukaskucera.numberneighbors.entity;

import com.lukaskucera.numberneighbors.enums.AnwserType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "answers")
@SuppressWarnings("NullAway.Init")
public class AnswerEntity extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "type", updatable = false, nullable = false)
  private AnwserType type;

  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "response_id", updatable = false, nullable = false)
  private ResponseEntity response;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "player_id", updatable = false, nullable = false)
  private PlayerEntity player;

  public AnswerEntity() {}

  public AnswerEntity(AnwserType type, ResponseEntity response) {
    setType(type);
    setResponse(response);
    setPlayer(response.getPlayer().getOpponent());
  }

  public AnwserType getType() {
    return type;
  }

  public void setType(AnwserType type) {
    this.type = type;
  }

  public ResponseEntity getResponse() {
    return response;
  }

  public void setResponse(ResponseEntity response) {
    this.response = response;
  }

  public PlayerEntity getPlayer() {
    return player;
  }

  public void setPlayer(PlayerEntity player) {
    this.player = player;
  }
}
