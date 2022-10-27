package com.lukaskucera.numberneighbors.service;

public interface NumberService {
  boolean isNumberInRange(int number);

  boolean areNumbersDistinct(int... numbers);

  void validateNumbers(int... numbers);
}
