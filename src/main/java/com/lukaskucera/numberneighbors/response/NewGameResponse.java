package com.lukaskucera.numberneighbors.response;

import com.lukaskucera.numberneighbors.entity.Game;

public record NewGameResponse(Game game, String token) {}
