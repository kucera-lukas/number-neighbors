import type AnswerType from "./answer-type.enum";
import type Payload from "./payload.type";

interface AnswerPayload extends Payload {
  type: AnswerType;
}

export default AnswerPayload;
