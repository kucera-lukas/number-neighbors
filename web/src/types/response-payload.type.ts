import type AnswerPayload from "./answer-payload.type";
import type Payload from "./payload.type";

interface ResponsePayload extends Payload {
  type: ResponseType;
  answer?: AnswerPayload;
}

export default ResponsePayload;
