package com.lukaskucera.numberneighbors.request;

import javax.validation.constraints.NotBlank;

public record NewGameRequest(@NotBlank String hostName) {}
