package com.lukaskucera.numberneighbors.service;

import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.dto.ResponseDTO;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.entity.ResponseEntity;
import com.lukaskucera.numberneighbors.entity.TurnEntity;
import com.lukaskucera.numberneighbors.enums.ResponseType;
import com.lukaskucera.numberneighbors.exception.AnswerAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.ResponseAlreadyExistsException;
import com.lukaskucera.numberneighbors.exception.ResponseNotFoundException;
import com.lukaskucera.numberneighbors.exception.ResponsePassedException;
import com.lukaskucera.numberneighbors.exception.TurnNotFoundException;
import com.lukaskucera.numberneighbors.repository.ResponseRepository;
import com.lukaskucera.numberneighbors.repository.TurnRepository;
import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl implements ResponseService {

  private final PlayerServiceImpl playerService;
  private final TurnServiceImpl turnService;

  private final TurnRepository turnRepository;
  private final ResponseRepository responseRepository;

  public ResponseServiceImpl(
    PlayerServiceImpl playerService,
    TurnServiceImpl turnService,
    TurnRepository turnRepository,
    ResponseRepository responseRepository
  ) {
    this.playerService = playerService;
    this.turnService = turnService;
    this.turnRepository = turnRepository;
    this.responseRepository = responseRepository;
  }

  @Override
  public ResponseEntity getResponseById(Long id) {
    return responseRepository
      .findById(id)
      .orElseThrow(() -> new ResponseNotFoundException(id));
  }

  @Override
  public List<ResponseDTO> getResponsesByPlayerId(AuthDTO auth, Long playerId) {
    playerService.checkPlayerAccess(auth, playerId);

    return responseRepository
      .findResponseEntitiesByPlayerId(playerId)
      .stream()
      .map(ResponseDTO::fromResponse)
      .toList();
  }

  @Override
  public ResponseDTO newResponse(AuthDTO auth, Long turnId, ResponseType type) {
    final TurnEntity turn = turnRepository
      .findById(turnId)
      .orElseThrow(() -> new TurnNotFoundException(turnId));
    final PlayerEntity player = turn.getPlayer().getOpponent();

    playerService.checkPlayerAccess(auth, player.getId());

    if (!turn.needsResponse()) {
      throw new ResponseAlreadyExistsException(turn.getId());
    }

    final ResponseEntity response = createResponse(type, turn);

    turnService.sendTurnToPlayers(turn);

    return ResponseDTO.fromResponse(response);
  }

  public ResponseEntity createResponse(ResponseType type, TurnEntity turn) {
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
