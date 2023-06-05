import ChooseNumbers from "./choose-numbers.component";
import GameInfo from "./game-info.component";
import GameInvite from "./game-invite.component";
import GamePlay from "./game-play.component";

import AccordionLayout from "../../layouts/accordion.layout";

import { memo } from "react";

// we want to maintain the internal accordion state in each re-render
const GameAccordion = memo(() => {
  return (
    <AccordionLayout defaultValues={["game-info"]}>
      <GameInfo />
      <GameInvite />
      <ChooseNumbers />
      <GamePlay />
    </AccordionLayout>
  );
});

GameAccordion.displayName = "GameAccordion";

export default GameAccordion;
