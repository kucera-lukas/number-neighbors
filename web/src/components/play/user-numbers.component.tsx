import NumberGrid from "./number-grid.component";

import { useGamePayload } from "../../context/game-payload.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";
import NumberSelectionType from "../../types/number-selection.enum";
import { classifyUserNumbers, toDiv } from "../../utils/number.utils";

import { useEffect, useState } from "react";

import type { ClassifiedUserNumber } from "../../types/classified-number.type";
import type ThemedNumber from "../../types/themed-number.type";

const COLOR_MAPPING = {
  [NumberSelectionType.CHOSEN]: { color: "red", backgroundColor: "orange" },
  [NumberSelectionType.GUESSED]: { color: "navy", backgroundColor: "blue" },
  [NumberSelectionType.REACHABLE]: {
    color: "orange",
    backgroundColor: "yellow",
  },
  [NumberSelectionType.UNREACHABLE]: {
    color: "black",
    backgroundColor: "gray",
  },
};

const toThemed = (number: ClassifiedUserNumber): ThemedNumber => {
  const color = COLOR_MAPPING[number.type];

  return {
    value: number.value,
    color: color.color,
    backgroundColor: color.backgroundColor,
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
      <NumberGrid cols={8}>
        {themedNumbers.map((number, i) => toDiv(number, i))}
      </NumberGrid>
    </AccordionItemLayout>
  );
};

export default UserNumbers;
