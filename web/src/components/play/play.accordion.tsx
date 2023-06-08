import OpponentNumbers from "./opponent-numbers.component";
import TurnHistory from "./turn-history.component";
import UserNumbers from "./user-numbers.component";

import AccordionLayout from "../../layouts/accordion.layout";

import { memo } from "react";

// we want to maintain the internal accordion state in each re-render
const PlayAccordion = memo(() => (
  <AccordionLayout defaultValues={["user-numbers", "opponent-numbers"]}>
    <UserNumbers />
    <OpponentNumbers />
    <TurnHistory />
  </AccordionLayout>
));

PlayAccordion.displayName = "PlayAccordion";

export default PlayAccordion;
