import { useGamePayload } from "../../context/game-payload.context";
import AnswerType from "../../types/answer-type.enum";

import { Code, List, Text } from "@mantine/core";

import type TurnPayload from "../../types/turn-payload.type";

export type TurnHistoryItemProps = {
  entry: number;
  turn: TurnPayload;
};

const VERB_COLOR_MAP = {
  guessed: "green",
  missed: "red",
  passed: "blue",
};

const getColoredVerb = (turn: TurnPayload): JSX.Element => {
  const verb =
    turn.response && turn.response.answer
      ? turn.response.answer.type === AnswerType.YES
        ? "guessed"
        : "missed"
      : "passed";

  return (
    <Text
      span
      c={VERB_COLOR_MAP[verb]}
    >
      {verb}
    </Text>
  );
};

const getValue = (turn: TurnPayload, turnOwner: boolean): JSX.Element => {
  return (
    <Code
      block={false}
      color={turnOwner ? "blue" : "red"}
    >
      {turn.value}
    </Code>
  );
};

const TurnHistoryItem = ({
  entry,
  turn,
}: TurnHistoryItemProps): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const turnOwner = gamePayload?.user.id === turn.playerId;

  const name = turnOwner ? "Opponent" : "I";
  const coloredVerb = getColoredVerb(turn);
  const value = getValue(turn, turnOwner);

  return (
    <List.Item key={entry}>
      <Text size="xs">
        {name} {coloredVerb} {value}
      </Text>
    </List.Item>
  );
};

export default TurnHistoryItem;
