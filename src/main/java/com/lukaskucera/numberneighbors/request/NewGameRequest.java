package com.lukaskucera.numberneighbors.request;

import javax.validation.constraints.NotBlank;

public record NewGameRequest(
  @NotBlank(message = "Host name can't be blank") String hostName
) {}
