import type Payload from "./payload.type";
import type ResponsePayload from "./response-payload.type";

interface TurnPayload extends Payload {
  value: number;
  response?: ResponsePayload;
  complete: boolean;
}

export default TurnPayload;
