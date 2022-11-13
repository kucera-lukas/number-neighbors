import { useGamePayload } from "../../context/game-payload.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { List, Stack, Text } from "@mantine/core";

const GameInfo = (): JSX.Element => {
  const [gamePayload] = useGamePayload();

  return (
    <AccordionItemLayout
      title="Game Info"
      value="game-info"
    >
      <Stack spacing="xs">
        <List>
          <List.Item>
            <Text size="sm">
              <>Game ID: {gamePayload?.id}</>
            </Text>
          </List.Item>
          <List.Item>
            <Text size="sm">
              <>Player ID: {gamePayload?.player?.id}</>
            </Text>
          </List.Item>
          <List.Item>
            <Text size="sm">
              <>Opponent player ID: {gamePayload?.opponent?.id}</>
            </Text>
          </List.Item>
        </List>
      </Stack>
    </AccordionItemLayout>
  );
};

export default GameInfo;
