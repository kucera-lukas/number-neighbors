package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import com.lukaskucera.numberneighbors.exception.PlayerNotOnTurnException;
import com.lukaskucera.numberneighbors.exception.TurnNotCompletedException;
import com.lukaskucera.numberneighbors.exception.TurnRequiresAvailableNumberException;
import com.lukaskucera.numberneighbors.exception.TurnRequiresChosenNumberException;
import com.lukaskucera.numberneighbors.repository.TurnRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TurnServiceImpl implements TurnService {

  private final TurnRepository turnRepository;

  TurnServiceImpl(TurnRepository turnRepository) {
    this.turnRepository = turnRepository;
  }

  @Override
  public List<TurnEntity> getTurnsByGameId(Long id) {
    return turnRepository.findTurnEntitiesByGameId(id);
  }

  @Override
  public TurnEntity newTurn(PlayerEntity player, int value) {
    checkGameTurns(player);
    checkNumbers(player, value);

    final TurnEntity turn = new TurnEntity(value, player);

    turnRepository.save(turn);

    return turn;
  }

  public void checkGameTurns(PlayerEntity player) {
    final List<TurnEntity> gameTurns = player.getGame().getTurns();
    final int gameTurnCount = gameTurns.size();

    if (gameTurnCount == 0) {
      checkFirstTurn(player);
    } else {
      checkLastTurn(player, gameTurns.get(gameTurnCount - 1));
    }
  }

  public void checkNumbers(PlayerEntity player, int value) {
    checkChosenNumbers(player, value);
    checkAvailableNumbers(player, value);
  }

  public void checkFirstTurn(PlayerEntity player) {
    if (player.isGuest()) {
      throw new PlayerNotOnTurnException(player.getId());
    }
  }

  public void checkLastTurn(PlayerEntity player, TurnEntity lastTurn) {
    // current player must not have played the last turn
    if (lastTurn.getId().equals(player.getId())) {
      throw new PlayerNotOnTurnException(player.getId());
    }

    if (!lastTurn.isComplete()) {
      throw new TurnNotCompletedException(lastTurn.getId());
    }
  }

  public void checkChosenNumbers(PlayerEntity player, int value) {
    final List<TurnEntity> playerTurns = player.getTurns();
    final int playerTurnCount = playerTurns.size();

    if (playerTurnCount < 2) {
      return;
    }

    final List<Integer> chosenNumbers = player
      .getNumbers()
      .stream()
      .filter(number -> !number.getIsGuessed())
      .map(NumberEntity::getValue)
      .toList();
    final boolean mustBeChosen = playerTurns
      .subList(playerTurnCount - 2, playerTurnCount)
      .stream()
      .noneMatch(turn -> chosenNumbers.contains(turn.getValue()));

    if (mustBeChosen && !chosenNumbers.contains(value)) {
      throw new TurnRequiresChosenNumberException(value);
    }
  }

  public void checkAvailableNumbers(PlayerEntity player, int value) {
    player
      .getNumbers()
      .stream()
      .filter(number -> !number.getIsGuessed())
      .map(NumberEntity::getAvailableNumbers)
      .flatMap(List::stream)
      .distinct()
      .filter(number -> number.equals(value))
      .findFirst()
      .orElseThrow(() -> new TurnRequiresAvailableNumberException(value));
  }
}
