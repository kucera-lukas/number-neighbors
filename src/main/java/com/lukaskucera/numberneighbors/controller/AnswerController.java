package com.lukaskucera.numberneighbors.controller;

import com.lukaskucera.numberneighbors.dto.AnswerDTO;
import com.lukaskucera.numberneighbors.dto.AuthDTO;
import com.lukaskucera.numberneighbors.request.NewAnswerRequest;
import com.lukaskucera.numberneighbors.service.AnswerServiceImpl;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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

  private final AnswerServiceImpl answerService;

  public AnswerController(AnswerServiceImpl answerService) {
    this.answerService = answerService;
  }

  @GetMapping("/answers")
  public ResponseEntity<List<AnswerDTO>> answers(
    @RequestParam(name = "player") Long playerId,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info("Answers requested by player {}", jwtToken.getName());

    final List<AnswerDTO> answers = answerService.getAnswersByPlayerId(
      AuthDTO.fromJwtToken(jwtToken),
      playerId
    );

    return ResponseEntity.ok(answers);
  }

  @PostMapping("/answers")
  public ResponseEntity<AnswerDTO> newAnswer(
    @RequestParam(name = "response") Long responseId,
    @Valid @RequestBody NewAnswerRequest newAnswerRequest,
    JwtAuthenticationToken jwtToken
  ) {
    logger.info(
      "New answer to response {} by player {}",
      responseId,
      jwtToken.getName()
    );

    final AnswerDTO answer = answerService.newAnswer(
      AuthDTO.fromJwtToken(jwtToken),
      responseId,
      newAnswerRequest.type()
    );

    return ResponseEntity.ok(answer);
  }
}
