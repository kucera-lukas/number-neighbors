package com.lukaskucera.numberneighbors.request;

import javax.validation.constraints.NotBlank;

public record NewPlayerRequest(@NotBlank String name) {}
