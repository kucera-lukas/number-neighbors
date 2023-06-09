import { useGamePayload } from "../../context/game-payload.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";
import { classifyOpponentNumbers, toBadge } from "../../utils/number.utils";

import { Group } from "@mantine/core";
import { useEffect, useState } from "react";

import type { ClassifiedOpponentNumber } from "../../types/classified-number.type";
import type ThemedNumber from "../../types/themed-number.type";

const toThemed = (number: ClassifiedOpponentNumber): ThemedNumber => {
  return {
    value: number.value,
    color: number.guessed ? "yellow.5" : "orange.5",
    border: number.guessed ? "2px solid #f59f00" : "2px solid #f76707",
    size: "lg",
  };
};

const OpponentNumbers = (): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const opponentNumbers = gamePayload?.opponent?.numbers;
  const [themedNumbers, setThemedNumbers] = useState<ThemedNumber[]>([]);

  useEffect(
    () =>
      setThemedNumbers(
        classifyOpponentNumbers(opponentNumbers ?? []).map((number) =>
          toThemed(number),
        ),
      ),
    [opponentNumbers, setThemedNumbers],
  );

  return (
    <AccordionItemLayout
      title="Opponent numbers"
      value="opponent-numbers"
    >
      <Group
        spacing="xl"
        position="center"
      >
        {themedNumbers.map((number, i) => toBadge(number, i))}
      </Group>
    </AccordionItemLayout>
  );
};

export default OpponentNumbers;
