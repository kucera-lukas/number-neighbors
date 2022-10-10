import type Entity from "./entity.type";
import type Player from "./player.type";

interface Game extends Entity {
  players: Player[];
}

export default Game;
