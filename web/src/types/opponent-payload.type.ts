import type Payload from "./payload.type";
import type PlayerType from "./player-type.enum";

interface OpponentPayload extends Payload {
  name: string;
  type: PlayerType;
  ready: boolean;
}

export default OpponentPayload;
