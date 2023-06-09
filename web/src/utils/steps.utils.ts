import type TurnPayload from "../types/turn-payload.type";
import type { StepProps } from "@mantine/core";

export const takeTurnStepProps = (
  currentTurn: TurnPayload | undefined,
  turnOwner: boolean,
): StepProps => {
  return {
    label: currentTurn ? `Turn number is ${currentTurn.value}` : "Take turn",
    allowStepSelect: false,
    description: currentTurn
      ? turnOwner
        ? "Number selected"
        : "Opponent selected number"
      : turnOwner
      ? "Select your number"
      : "Opponent is selecting number",
    loading: !turnOwner && !currentTurn,
  };
};

export const respondStepProps = (
  currentTurn: TurnPayload | undefined,
  turnOwner: boolean,
): StepProps => {
  return {
    label: currentTurn?.response
      ? `Response is "${currentTurn.response.type.toLowerCase()}"`
      : "Respond",
    allowStepSelect: false,
    description: currentTurn
      ? currentTurn?.response
        ? turnOwner
          ? "Opponent has responded"
          : "Responded to opponents turn"
        : turnOwner
        ? "Opponent is responding"
        : "Respond to opponents turn"
      : "Waiting for previous step",
    // current turn must be waiting for a response
    loading: turnOwner && currentTurn && !currentTurn?.response,
  };
};

export const answerStepProps = (
  currentTurn: TurnPayload | undefined,
  turnOwner: boolean,
): StepProps => {
  return {
    label: currentTurn?.response?.answer
      ? `Answer is "${currentTurn.response.answer.type.toLowerCase()}"`
      : "Answer",
    allowStepSelect: false,
    description: currentTurn?.response
      ? currentTurn.response.answer
        ? turnOwner
          ? "Answered to the response"
          : "Opponent answered to the response"
        : turnOwner
        ? "Answer to the response"
        : "Opponent is answering to the response"
      : "Waiting for previous step",
    // response to the current turn must be waiting for an answer
    loading:
      !turnOwner &&
      !currentTurn?.complete &&
      currentTurn?.response &&
      !currentTurn.response.answer,
  };
};
