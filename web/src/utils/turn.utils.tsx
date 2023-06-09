import type PlayerPayload from "../types/player-payload.type";
import type TurnPayload from "../types/turn-payload.type";

export const requiresChosenNumber = (
  player: PlayerPayload,
  playerTurns: TurnPayload[],
): boolean => {
  const playerTurnCount = playerTurns.length;

  if (playerTurnCount < 2) {
    return false;
  }

  const chosenNumbers = new Set(
    player.numbers
      .filter((number) => !number.guessed)
      .map((number) => number.value),
  );

  return !playerTurns
    .slice(playerTurnCount - 2)
    .some((turn) => chosenNumbers.has(turn.value));
};
