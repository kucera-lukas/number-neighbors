import type OpponentPayload from "./opponent-payload.type";
import type Payload from "./payload.type";
import type PlayerPayload from "./player-payload.type";

interface GamePayload extends Payload {
  ready: boolean;
  player?: PlayerPayload;
  opponent?: OpponentPayload;
}

export default GamePayload;
