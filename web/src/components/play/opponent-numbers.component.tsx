import NumberGrid from "./number-grid.component";

import { useGamePayload } from "../../context/game-payload.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";
import { classifyOpponentNumbers, toDiv } from "../../utils/number.utils";

import { useEffect, useState } from "react";

import type { ClassifiedOpponentNumber } from "../../types/classified-number.type";
import type ThemedNumber from "../../types/themed-number.type";

const toThemed = (number: ClassifiedOpponentNumber): ThemedNumber => {
  return {
    value: number.value,
    color: "blue",
    backgroundColor: "cyan",
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
      <NumberGrid cols={3}>
        {themedNumbers.map((number, i) => toDiv(number, i))}
      </NumberGrid>
    </AccordionItemLayout>
  );
};

export default OpponentNumbers;
