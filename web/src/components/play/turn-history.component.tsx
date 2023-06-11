import TurnHistoryColumn from "./turn-history-column.component";

import { useGamePayload } from "../../context/game-payload.context";
import { useTurns } from "../../context/turns.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { Divider, Group, ScrollArea } from "@mantine/core";
import { useEffect, useRef } from "react";

const TurnHistory = (): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const [turns] = useTurns();
  const viewport = useRef<HTMLDivElement>(null);

  const entries = [...turns.filter((turn) => turn.complete).entries()];

  useEffect(() => {
    viewport.current?.scrollTo({
      top: viewport.current.scrollHeight,
      behavior: "smooth",
    });
  }, [turns]);

  return (
    <AccordionItemLayout
      title="Turn History"
      value="turn-history"
    >
      <ScrollArea.Autosize
        type="auto"
        viewportRef={viewport}
        mah={300}
        offsetScrollbars
      >
        <Group
          position="center"
          spacing="md"
        >
          <TurnHistoryColumn
            playerId={gamePayload?.opponent?.id}
            entries={entries}
          />
          <Divider
            orientation="vertical"
            size="sm"
          />
          <TurnHistoryColumn
            playerId={gamePayload?.user?.id}
            entries={entries}
          />
        </Group>
      </ScrollArea.Autosize>
    </AccordionItemLayout>
  );
};

export default TurnHistory;
