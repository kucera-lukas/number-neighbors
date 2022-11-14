import type NumberPayload from "./number-payload.type";
import type Payload from "./payload.type";
import type PlayerType from "./player-type.enum";

interface UserPayload extends Payload {
  name: string;
  type: PlayerType;
  numbers: NumberPayload[];
  ready: boolean;
}

export default UserPayload;
