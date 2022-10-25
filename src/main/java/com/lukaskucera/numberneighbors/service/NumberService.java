package com.lukaskucera.numberneighbors.service;

public interface NumberService {
  boolean isNumberInRange(int number);

  boolean areNumbersDistinct(int first, int second, int third);

  void validateNumbers(int first, int second, int third);
}
