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
import java.util.Optional;
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
    final List<TurnEntity> gameTurns = player.getGame().getTurns();
    final int gameTurnCount = gameTurns.size();

    // host must be the first player
    if (gameTurnCount == 0 && player.isGuest()) {
      throw new PlayerNotOnTurnException(player.getId());
    }

    final Optional<TurnEntity> lastTurn = Optional.ofNullable(
      gameTurnCount == 0 ? null : gameTurns.get(gameTurnCount - 1)
    );

    lastTurn.ifPresent(turn -> {
      // current player must not have played the last turn
      if (turn.getId().equals(player.getId())) {
        throw new PlayerNotOnTurnException(player.getId());
      }

      if (!turn.isComplete()) {
        throw new TurnNotCompletedException(turn.getId());
      }
    });

    final List<NumberEntity> numbers = player.getNumbers();
    final List<Integer> chosenNumbers = numbers
      .stream()
      .filter(number -> !number.getIsGuessed())
      .map(NumberEntity::getValue)
      .toList();
    final List<TurnEntity> playerTurns = player.getTurns();
    final int playerTurnCount = playerTurns.size();

    final boolean mustBeChosen =
      playerTurnCount >= 2 &&
      playerTurns
        .subList(playerTurnCount - 2, playerTurnCount)
        .stream()
        .noneMatch(turn -> chosenNumbers.contains(turn.getValue()));

    if (mustBeChosen && !chosenNumbers.contains(value)) {
      throw new TurnRequiresChosenNumberException(value);
    }

    numbers
      .stream()
      .map(NumberEntity::getAvailableNumbers)
      .flatMap(List::stream)
      .distinct()
      .filter(number -> number.equals(value))
      .findFirst()
      .orElseThrow(() -> new TurnRequiresAvailableNumberException(value));

    final TurnEntity turn = new TurnEntity(value, player);

    turnRepository.save(turn);

    return turn;
  }
}
