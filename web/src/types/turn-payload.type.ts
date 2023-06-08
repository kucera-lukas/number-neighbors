import type Payload from "./payload.type";
import type ResponsePayload from "./response-payload.type";

interface TurnPayload extends Payload {
  playerId: bigint;
  value: number;
  response?: ResponsePayload;
  complete: boolean;
}

export default TurnPayload;
