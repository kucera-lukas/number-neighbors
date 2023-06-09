import GameInfoBadge from "./game-info-badge";

import { useGamePayload } from "../../context/game-payload.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { Stack, Text } from "@mantine/core";

const GameInfo = (): JSX.Element => {
  const [gamePayload] = useGamePayload();

  return (
    <AccordionItemLayout
      title="Game Info"
      value="game-info"
    >
      <Stack
        spacing="xs"
        align="center"
        justify="center"
      >
        <GameInfoBadge
          player={gamePayload?.user}
          color="blue"
        />
        <Text size="sm">vs</Text>
        <GameInfoBadge
          player={gamePayload?.opponent}
          color="red"
        />
      </Stack>
    </AccordionItemLayout>
  );
};

export default GameInfo;
