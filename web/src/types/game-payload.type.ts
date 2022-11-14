import type Payload from "./payload.type";
import type PlayerPayload from "./player-payload.type";
import type UserPayload from "./user-payload.type";

interface GamePayload extends Payload {
  ready: boolean;
  player?: UserPayload;
  opponent?: PlayerPayload;
}

export default GamePayload;
