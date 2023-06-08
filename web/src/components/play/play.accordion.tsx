import CurrentTurn from "./current-turn.component";
import OpponentNumbers from "./opponent-numbers.component";
import TurnHistory from "./turn-history.component";
import UserNumbers from "./user-numbers.component";

import { useTurns } from "../../context/turns.context";
import AccordionLayout from "../../layouts/accordion.layout";

import { memo, useState } from "react";

// we want to maintain the internal accordion state in each re-render
const PlayAccordion = memo(() => {
  const [turns] = useTurns();
  const [turnIndex, setTurnIndex] = useState<number>(turns.length - 1);

  return (
    <AccordionLayout
      defaultValues={["user-numbers", "opponent-numbers", "current-turn"]}
    >
      <UserNumbers />
      <OpponentNumbers />
      <CurrentTurn
        turnIndex={turnIndex}
        setTurnIndex={setTurnIndex}
      />
      <TurnHistory />
    </AccordionLayout>
  );
});

PlayAccordion.displayName = "PlayAccordion";

export default PlayAccordion;
