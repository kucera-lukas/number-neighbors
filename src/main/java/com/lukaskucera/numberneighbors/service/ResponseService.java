package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import com.lukaskucera.numberneighbors.enums.ResponseType;
import java.util.List;

public interface ResponseService {
  ResponseEntity getResponseById(Long id);

  List<ResponseEntity> getResponsesByPlayerId(Long id);

  ResponseEntity newResponse(TurnEntity turn, ResponseType type);

  void checkResponseNeedsAnswer(ResponseEntity response);
}
