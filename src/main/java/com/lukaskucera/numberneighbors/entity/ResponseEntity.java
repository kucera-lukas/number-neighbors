package com.lukaskucera.numberneighbors.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lukaskucera.numberneighbors.enums.ResponseType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "responses")
@SuppressWarnings("NullAway.Init")
public class ResponseEntity extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "type", updatable = false, nullable = false)
  private ResponseType type;

  @OneToOne(mappedBy = "response")
  @Nullable
  private AnswerEntity answer;

  @OneToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "turn_id", updatable = false, nullable = false)
  @JsonBackReference
  private TurnEntity turn;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "player_id", updatable = false, nullable = false)
  @JsonBackReference
  private PlayerEntity player;

  public ResponseEntity() {}

  public ResponseEntity(ResponseType type, TurnEntity turn) {
    setType(type);
    setTurn(turn);
    setPlayer(turn.getPlayer().getOtherPlayer());
  }

  public ResponseType getType() {
    return type;
  }

  public void setType(ResponseType type) {
    this.type = type;
  }

  @Nullable
  public AnswerEntity getAnswer() {
    return answer;
  }

  public void setAnswer(@Nullable AnswerEntity answer) {
    this.answer = answer;

    if (answer != null) {
      answer.setResponse(this);
    }
  }

  public TurnEntity getTurn() {
    return turn;
  }

  public void setTurn(TurnEntity turn) {
    this.turn = turn;
  }

  public PlayerEntity getPlayer() {
    return player;
  }

  public void setPlayer(PlayerEntity player) {
    this.player = player;
  }
}
