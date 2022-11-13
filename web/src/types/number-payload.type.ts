import type NumberType from "./number-type.enum";
import type Payload from "./payload.type";

interface NumberPayload extends Payload {
  value: number;
  guessed: boolean;
  type: NumberType;
}

export default NumberPayload;
