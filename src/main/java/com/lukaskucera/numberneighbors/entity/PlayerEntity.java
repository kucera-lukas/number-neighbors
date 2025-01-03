package com.lukaskucera.numberneighbors.entity;

import com.lukaskucera.numberneighbors.enums.PlayerType;
import com.lukaskucera.numberneighbors.exception.GuestPlayerMissingException;
import com.lukaskucera.numberneighbors.exception.HostPlayerMissingException;
import java.util.List;
import java.util.Optional;
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
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.OrderBy;

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
  @OrderBy(clause = "type ASC")
  private final List<NumberEntity> numbers;

  @OneToMany(
    mappedBy = "player",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @OrderBy(clause = "created ASC")
  private final List<TurnEntity> turns;

  @OneToMany(
    mappedBy = "player",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @OrderBy(clause = "created ASC")
  private final List<ResponseEntity> responses;

  @OneToMany(
    mappedBy = "player",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  @OrderBy(clause = "created ASC")
  private final List<AnswerEntity> answers;

  @Column(name = "name", nullable = false)
  @NotBlank(message = "Player name can't be blank")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", updatable = false, nullable = false)
  private PlayerType type;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id", updatable = false, nullable = false)
  private GameEntity game;

  public PlayerEntity() {
    this.numbers = List.of();
    this.turns = List.of();
    this.responses = List.of();
    this.answers = List.of();
  }

  public PlayerEntity(String name, GameEntity game) {
    this(name, game, List.of(), List.of(), List.of(), List.of());
  }

  public PlayerEntity(
    String name,
    GameEntity game,
    List<NumberEntity> numbers,
    List<TurnEntity> turns,
    List<ResponseEntity> responses,
    List<AnswerEntity> answers
  ) {
    setName(name);
    setType(game.getPlayers().isEmpty() ? PlayerType.HOST : PlayerType.GUEST);
    setGame(game);
    this.numbers = numbers;
    this.turns = turns;
    this.responses = responses;
    this.answers = answers;
  }

  public List<NumberEntity> getNumbers() {
    return List.copyOf(numbers);
  }

  public List<TurnEntity> getTurns() {
    return List.copyOf(turns);
  }

  public List<ResponseEntity> getResponses() {
    return List.copyOf(responses);
  }

  public List<AnswerEntity> getAnswers() {
    return List.copyOf(answers);
  }

  public void addNumber(NumberEntity number) {
    numbers.add(number);
    number.setPlayer(this);
  }

  public void addTurn(TurnEntity turn) {
    turns.add(turn);
    turn.setPlayer(this);
  }

  public void addResponse(ResponseEntity response) {
    responses.add(response);
    response.setPlayer(this);
  }

  public void addAnswer(AnswerEntity answer) {
    answers.add(answer);
    answer.setPlayer(this);
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

  public String getSub() {
    return getId().toString();
  }

  public PlayerEntity getOpponent() {
    return isHost()
      ? game
        .getGuest()
        .orElseThrow(() -> new GuestPlayerMissingException(game.getId()))
      : game
        .getHost()
        .orElseThrow(() -> new HostPlayerMissingException(game.getId()));
  }

  public Boolean isHost() {
    return type == PlayerType.HOST;
  }

  public Optional<PlayerEntity> getOpponentOptional() {
    return isHost() ? game.getGuest() : game.getHost();
  }

  public GameEntity getGame() {
    return game;
  }

  public void setGame(GameEntity game) {
    this.game = game;
  }

  public Boolean isGuest() {
    return type == PlayerType.GUEST;
  }

  public boolean isReady() {
    return numbers.size() == 3;
  }
}
