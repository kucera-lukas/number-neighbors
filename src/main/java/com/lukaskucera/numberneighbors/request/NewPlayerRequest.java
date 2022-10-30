package com.lukaskucera.numberneighbors.request;

import javax.validation.constraints.NotBlank;

public record NewPlayerRequest(
  @NotBlank(message = "Player name can't be blank") String name
) {}
