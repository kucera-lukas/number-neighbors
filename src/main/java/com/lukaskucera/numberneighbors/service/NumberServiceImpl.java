package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.enums.NumberType;
import com.lukaskucera.numberneighbors.exception.NumberOutOfRangeException;
import com.lukaskucera.numberneighbors.exception.NumbersNotDistinctException;
import com.lukaskucera.numberneighbors.exception.NumbersPopulatedException;
import com.lukaskucera.numberneighbors.repository.NumberRepository;
import com.lukaskucera.numberneighbors.repository.PlayerRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class NumberServiceImpl implements NumberService {

  public static final int NUMBER_COUNT_PER_PLAYER = 3;
  public static final int MIN_NUMBER = 0;
  public static final int MAX_NUMBER = 15;

  private final PlayerRepository playerRepository;
  private final NumberRepository numberRepository;

  public NumberServiceImpl(
    PlayerRepository playerRepository,
    NumberRepository numberRepository
  ) {
    this.playerRepository = playerRepository;
    this.numberRepository = numberRepository;
  }

  @Override
  public List<NumberEntity> getNumbersByPlayerId(Long playerId) {
    return numberRepository.findNumberEntitiesByPlayerId(playerId);
  }

  @Override
  @Transactional
  public List<NumberEntity> addNumbersToPlayer(
    PlayerEntity player,
    int first,
    int second,
    int third
  ) {
    if (!player.getNumbers().isEmpty()) {
      throw new NumbersPopulatedException(player.getId());
    }

    Map
      .of(
        NumberType.FIRST,
        first,
        NumberType.SECOND,
        second,
        NumberType.THIRD,
        third
      )
      .entrySet()
      .stream()
      .map(entry -> new NumberEntity(entry.getValue(), entry.getKey(), player))
      .forEach(player::addNumber);

    playerRepository.save(player);

    return player.getNumbers();
  }

  @Override
  public boolean isNumberInRange(int number) {
    return number >= MIN_NUMBER && number <= MAX_NUMBER;
  }

  @Override
  public boolean areNumbersDistinct(int... numbers) {
    final Set<Integer> seen = new HashSet<>();

    for (int number : numbers) {
      if (!seen.add(number)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public void validateNumbers(int... numbers) {
    if (!areNumbersDistinct(numbers)) {
      throw new NumbersNotDistinctException(numbers);
    }

    Arrays
      .stream(numbers)
      .forEach(number -> {
        if (!isNumberInRange(number)) {
          throw new NumberOutOfRangeException(number);
        }
      });
  }
}
