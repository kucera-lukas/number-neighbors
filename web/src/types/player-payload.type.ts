import type Payload from "./payload.type";
import type PlayerType from "./player-type.enum";

interface PlayerPayload extends Payload {
  name: string;
  type: PlayerType;
  ready: boolean;
}

export default PlayerPayload;
