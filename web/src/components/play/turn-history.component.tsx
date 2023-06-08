import TurnHistoryItem from "./turn-history-item.component";

import { useTurns } from "../../context/turns.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { List, ScrollArea } from "@mantine/core";

const TurnHistory = (): JSX.Element => {
  const [turns] = useTurns();

  return (
    <AccordionItemLayout
      title="Turn History"
      value="turn-history"
    >
      <List
        type="ordered"
        size="xs"
      >
        <ScrollArea.Autosize
          maxHeight={100}
          type="auto"
          offsetScrollbars
        >
          {[...turns.entries()].map(([i, turn]) => (
            <TurnHistoryItem
              key={i}
              entry={i}
              turn={turn}
            />
          ))}
        </ScrollArea.Autosize>
      </List>
    </AccordionItemLayout>
  );
};

export default TurnHistory;
