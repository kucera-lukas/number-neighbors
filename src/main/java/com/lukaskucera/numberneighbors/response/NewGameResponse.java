package com.lukaskucera.numberneighbors.response;

import com.lukaskucera.numberneighbors.entity.GameEntity;

public record NewGameResponse(GameEntity game, String token) {}
