package com.lukaskucera.numberneighbors.response;

import com.lukaskucera.numberneighbors.entity.PlayerEntity;

public record NewPlayerResponse(PlayerEntity player, String token) {}
