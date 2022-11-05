package com.lukaskucera.numberneighbors.request;

import com.lukaskucera.numberneighbors.service.NumberServiceImpl;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record NewNumbersRequest(
  @NotNull
  @Range(min = NumberServiceImpl.MIN_NUMBER, max = NumberServiceImpl.MAX_NUMBER)
  int first,
  @NotNull
  @Range(min = NumberServiceImpl.MIN_NUMBER, max = NumberServiceImpl.MAX_NUMBER)
  int second,
  @NotNull
  @Range(min = NumberServiceImpl.MIN_NUMBER, max = NumberServiceImpl.MAX_NUMBER)
  int third
) {}
