package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.NumberEntity;
import com.lukaskucera.numberneighbors.exception.NumberOutOfRangeException;
import com.lukaskucera.numberneighbors.exception.NumbersNotDistinctException;
import com.lukaskucera.numberneighbors.repository.NumberRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class NumberServiceImpl implements NumberService {

  public static final int NUMBER_COUNT_PER_PLAYER = 3;
  public static final int MIN_NUMBER = 0;
  public static final int MAX_NUMBER = 15;

  private final NumberRepository numberRepository;

  public NumberServiceImpl(NumberRepository numberRepository) {
    this.numberRepository = numberRepository;
  }

  @Override
  public List<NumberEntity> getNumbersByPlayerId(Long playerId) {
    return numberRepository.findNumberEntitiesByPlayerId(playerId);
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
