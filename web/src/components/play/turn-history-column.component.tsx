import TurnHistoryItem from "./turn-history-item.component";

import { List } from "@mantine/core";

import type TurnPayload from "../../types/turn-payload.type";

export interface TurnHistoryColumnProps {
  playerId?: bigint;
  entries: [number, TurnPayload][];
}

const TurnHistoryColumn = ({
  playerId,
  entries,
}: TurnHistoryColumnProps): JSX.Element => {
  return (
    <List
      size="xs"
      type="ordered"
    >
      {entries
        .filter(([, turn]) => turn.playerId === playerId)
        .map(([i, turn]) => (
          <TurnHistoryItem
            key={i}
            entry={i}
            turn={turn}
          />
        ))}
    </List>
  );
};

export default TurnHistoryColumn;
