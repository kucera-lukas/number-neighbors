package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AnswerDTO;
import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.enums.AnswerType;
import java.util.List;

public interface AnswerService {
  List<AnswerDTO> getAnswersByPlayerId(AuthDTO auth, Long playerId);

  AnswerDTO newAnswer(AuthDTO auth, Long responseId, AnswerType type);
}
