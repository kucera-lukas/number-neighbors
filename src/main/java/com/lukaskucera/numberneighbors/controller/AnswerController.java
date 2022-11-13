package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.entity.AnswerEntity;
import com.lukaskucera.numberneighbors.entity.PlayerEntity;
import com.lukaskucera.numberneighbors.payload.TurnPayload;
import com.lukaskucera.numberneighbors.request.NewAnswerRequest;
import com.lukaskucera.numberneighbors.service.AnswerServiceImpl;
import com.lukaskucera.numberneighbors.service.PlayerServiceImpl;
import com.lukaskucera.numberneighbors.service.ResponseServiceImpl;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerController {

  private static final Logger logger = LoggerFactory.getLogger(
    AnswerController.class
  );

  private final PlayerServiceImpl playerService;
  private final ResponseServiceImpl responseService;
  private final AnswerServiceImpl answerService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  public AnswerController(
    PlayerServiceImpl playerService,
    ResponseServiceImpl responseService,
    AnswerServiceImpl answerService,
    SimpMessagingTemplate simpMessagingTemplate
  ) {
    this.playerService = playerService;
    this.responseService = responseService;
    this.answerService = answerService;
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  @GetMapping("/answers")
  public ResponseEntity<List<AnswerEntity>> answers(
    @RequestParam(name = "player") Long playerId,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Answers requested by player {}", jwtToken.getName());

    playerService.checkPlayerAccess(playerId, jwtToken);

    return ResponseEntity.ok(answerService.getAnswersByPlayerId(playerId));
  }

  @PostMapping("/answers")
  public ResponseEntity<AnswerEntity> newAnswer(
    @RequestParam(name = "response") Long responseId,
    @Valid @RequestBody NewAnswerRequest newAnswerRequest,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "New answer to response {} by player {}",
      responseId,
      jwtToken.getName()
    );

    final com.lukaskucera.numberneighbors.entity.ResponseEntity response = responseService.getResponseById(
      responseId
    );
    final PlayerEntity opponent = response.getPlayer();
    final PlayerEntity player = opponent.getOpponent();

    playerService.checkPlayerAccess(player.getId(), jwtToken);
    responseService.checkResponseNeedsAnswer(response);

    final AnswerEntity answer = answerService.newAnswer(
      response,
      newAnswerRequest.type()
    );

    simpMessagingTemplate.convertAndSendToUser(
      opponent.getSub(),
      "/queue/turns",
      TurnPayload.fromTurn(response.getTurn())
    );

    return ResponseEntity.ok(answer);
  }
}
