import type AnswerType from "./answer-type.enum";
import type Payload from "./payload.type";

interface AnswerPayload extends Payload {
  playerId: bigint;

  type: AnswerType;
}

export default AnswerPayload;
