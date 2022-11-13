package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import com.lukaskucera.numberneighbors.enums.ResponseType;
import com.lukaskucera.numberneighbors.exception.AnswerAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.ResponseNotFoundException;
import com.lukaskucera.numberneighbors.exception.ResponsePassedException;
import com.lukaskucera.numberneighbors.repository.ResponseRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl implements ResponseService {

  private final ResponseRepository responseRepository;

  public ResponseServiceImpl(ResponseRepository responseRepository) {
    this.responseRepository = responseRepository;
  }

  @Override
  public ResponseEntity getResponseById(Long id) {
    return responseRepository
      .findById(id)
      .orElseThrow(() -> new ResponseNotFoundException(id));
  }

  @Override
  public List<ResponseEntity> getResponsesByPlayerId(Long id) {
    return responseRepository.findResponseEntitiesByPlayerId(id);
  }

  @Override
  public ResponseEntity newResponse(TurnEntity turn, ResponseType type) {
    final ResponseEntity response = new ResponseEntity(type, turn);

    turn.setResponse(response);
    turn.getPlayer().getOpponent().addResponse(response);

    responseRepository.save(response);

    return response;
  }

  @Override
  public void checkResponseNeedsAnswer(ResponseEntity response) {
    if (response.getType() == ResponseType.PASS) {
      throw new ResponsePassedException(response.getId());
    }

    if (response.getAnswer() != null) {
      throw new AnswerAlreadyExistsException(response.getId());
    }
  }
}
