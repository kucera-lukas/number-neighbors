import type AnswerPayload from "./answer-payload.type";
import type Payload from "./payload.type";

interface ResponsePayload extends Payload {
  playerId: bigint;

  type: ResponseType;
  answer?: AnswerPayload;
}

export default ResponsePayload;
