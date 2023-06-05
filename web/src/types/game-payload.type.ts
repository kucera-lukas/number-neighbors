import type Payload from "./payload.type";
import type PlayerPayload from "./player-payload.type";

interface GamePayload extends Payload {
  ready: boolean;
  user: PlayerPayload;
  opponent?: PlayerPayload;
}

export default GamePayload;
