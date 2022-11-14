package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.ResponseDTO;
import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import com.lukaskucera.numberneighbors.enums.ResponseType;
import java.util.List;

public interface ResponseService {
  ResponseEntity getResponseById(Long id);

  List<ResponseDTO> getResponsesByPlayerId(AuthDTO auth, Long playerId);

  ResponseDTO newResponse(AuthDTO auth, Long turnId, ResponseType type);

  void checkResponseNeedsAnswer(ResponseEntity response);
}
