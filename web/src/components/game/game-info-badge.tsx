import { Badge, Loader } from "@mantine/core";

import type PlayerPayload from "../../types/player-payload.type";
import type { MantineColor } from "@mantine/core";

export type GameInfoBadgeProps = {
  player: PlayerPayload | undefined;
  color: MantineColor;
};

const GameInfoBadge = ({ player, color }: GameInfoBadgeProps): JSX.Element => {
  return (
    <Badge color={color}>
      {player?.name ?? (
        <Loader
          size="sm"
          color={color}
          variant="dots"
        />
      )}
    </Badge>
  );
};

export default GameInfoBadge;
