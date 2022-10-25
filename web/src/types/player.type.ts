import type Entity from "./entity.type";
import type PlayerType from "./player-type.type";

interface Player extends Entity {
  name: string;
  type: PlayerType;
  numbers: number[];
}

export default Player;
