import ResponseType from "../types/response-type.enum";

import type ResponsePayload from "../types/response-payload.type";

export const requiresPassType = (
  type: ResponseType,
  playerResponses: ResponsePayload[],
): boolean => {
  if (type == ResponseType.PASS) {
    return false;
  }

  const lastResponse = playerResponses.at(-1);

  return lastResponse?.type == ResponseType.GUESS;
};
