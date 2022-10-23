package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.exception.NumberOutOfRangeException;
import com.lukaskucera.numberneighbors.exception.NumbersNotDistinctException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NumberServiceImpl implements NumberService {

  public static final int MIN_NUMBER = 0;
  public static final int MAX_NUMBER = 15;

  @Override
  public boolean isNumberInRange(int number) {
    return number >= MIN_NUMBER && number <= MAX_NUMBER;
  }

  @Override
  public boolean areNumbersDistinct(int first, int second, int third) {
    return first != second && first != third && second != third;
  }

  @Override
  public void validateNumbers(int first, int second, int third) {
    if (!areNumbersDistinct(first, second, third)) {
      throw new NumbersNotDistinctException(first, second, third);
    }

    List<Integer> numberList = List.of(first, second, third);

    numberList.forEach(number -> {
      if (!isNumberInRange(number)) {
        throw new NumberOutOfRangeException(number);
      }
    });
  }
}
