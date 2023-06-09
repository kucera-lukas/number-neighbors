import { useGamePayload } from "../../context/game-payload.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";
import NumberSelectionType from "../../types/number-selection.enum";
import { classifyUserNumbers, toBadge } from "../../utils/number.utils";

import { SimpleGrid } from "@mantine/core";
import { useEffect, useState } from "react";

import type { ClassifiedUserNumber } from "../../types/classified-number.type";
import type ThemedNumber from "../../types/themed-number.type";

const COLOR_MAPPING = {
  [NumberSelectionType.CHOSEN]: {
    color: "blue.5",
    border: "2px solid #1971c2",
  },
  [NumberSelectionType.GUESSED]: { color: "red.5", border: "2px solid red" },
  [NumberSelectionType.REACHABLE]: {
    color: "blue.3",
    border: "2px solid #339af0",
  },
  [NumberSelectionType.UNREACHABLE]: {
    color: "gray",
    border: "2px solid #495057",
  },
};

const toThemed = (number: ClassifiedUserNumber): ThemedNumber => {
  const color = COLOR_MAPPING[number.type];

  return {
    value: number.value,
    color: color.color,
    border: color.border,
    size: "sm",
  };
};

const UserNumbers = (): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const numbers = gamePayload?.user.numbers;
  const [themedNumbers, setThemedNumbers] = useState<ThemedNumber[]>([]);

  useEffect(
    () =>
      setThemedNumbers(
        classifyUserNumbers(numbers ?? []).map((number) => toThemed(number)),
      ),
    [numbers, setThemedNumbers],
  );

  return (
    <AccordionItemLayout
      title="My numbers"
      value="user-numbers"
    >
      <SimpleGrid
        spacing="xs"
        verticalSpacing="xs"
        cols={8}
      >
        {themedNumbers.map((number, i) => toBadge(number, i))}
      </SimpleGrid>
    </AccordionItemLayout>
  );
};

export default UserNumbers;
