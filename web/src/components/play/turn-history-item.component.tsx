import { useGamePayload } from "../../context/game-payload.context";

import { List, Text } from "@mantine/core";

import type TurnPayload from "../../types/turn-payload.type";
import type { MantineColor } from "@mantine/core";

export type TurnHistoryItemProps = {
  entry: number;
  turn: TurnPayload;
};

const createValueText = (
  name: string,
  value: string | undefined,
  color: MantineColor,
  comma: boolean,
): JSX.Element => {
  return (
    <span key={name}>
      {value !== undefined && (
        <>
          {comma && <Text span>, </Text>}
          {name}:{" "}
          <Text
            span
            c={color}
          >
            {value}
          </Text>
        </>
      )}
    </span>
  );
};

const TurnHistoryItem = ({
  entry,
  turn,
}: TurnHistoryItemProps): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const turnOwner = gamePayload?.user.id === turn.playerId;

  const args: [string, string | undefined, MantineColor, boolean][] = [
    ["value", turn.value.toString(), turnOwner ? "blue" : "red", false],
    [
      "response",
      turn.response?.type.toString(),
      turnOwner ? "red" : "blue",
      true,
    ],
    [
      "answer",
      turn.response?.answer?.type.toString(),
      turnOwner ? "blue" : "red",
      true,
    ],
  ];

  return (
    <List.Item key={entry}>
      <Text size="xs">{args.map((arg) => createValueText(...arg))}</Text>
    </List.Item>
  );
};

export default TurnHistoryItem;
