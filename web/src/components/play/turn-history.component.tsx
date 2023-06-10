import TurnHistoryColumn from "./turn-history-column.component";

import { useGamePayload } from "../../context/game-payload.context";
import { useTurns } from "../../context/turns.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { Group, ScrollArea } from "@mantine/core";

const TurnHistory = (): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const [turns] = useTurns();

  const entries = [...turns.filter((turn) => turn.complete).entries()];

  return (
    <AccordionItemLayout
      title="Turn History"
      value="turn-history"
    >
      <ScrollArea.Autosize
        h={100}
        type="auto"
        offsetScrollbars
      >
        <Group
          position="center"
          spacing="xl"
        >
          {[gamePayload?.opponent?.id, gamePayload?.user.id].map((playerId) => (
            <TurnHistoryColumn
              key={playerId?.toString()}
              playerId={playerId}
              entries={entries}
            />
          ))}
        </Group>
      </ScrollArea.Autosize>
    </AccordionItemLayout>
  );
};

export default TurnHistory;
