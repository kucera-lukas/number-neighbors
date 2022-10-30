package com.lukaskucera.numberneighbors.request;

import com.lukaskucera.numberneighbors.service.NumberServiceImpl;
import org.hibernate.validator.constraints.Range;

public record NewNumbersRequest(
  @Range(min = NumberServiceImpl.MIN_NUMBER, max = NumberServiceImpl.MAX_NUMBER)
  int first,
  @Range(min = NumberServiceImpl.MIN_NUMBER, max = NumberServiceImpl.MAX_NUMBER)
  int second,
  @Range(min = NumberServiceImpl.MIN_NUMBER, max = NumberServiceImpl.MAX_NUMBER)
  int third
) {}
