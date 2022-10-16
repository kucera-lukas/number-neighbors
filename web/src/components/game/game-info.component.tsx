import { useGame } from "../../context/game.context";
import AccordionLayout from "../../layouts/accordion.layout";
import PlayerType from "../../types/player-type.type";

import { List, Stack, Text } from "@mantine/core";

const GameInfo = (): JSX.Element => {
  const [game] = useGame();

  return (
    <AccordionLayout
      title="Game Info"
      value="game-info"
    >
      <Stack spacing="xs">
        <List>
          <List.Item>
            <Text size="sm">
              <>Game ID: {game.id}</>
            </Text>
          </List.Item>
          <List.Item>
            <Text size="sm">
              <>
                Host player ID:{" "}
                {
                  game.players.find((player) => player.type == PlayerType.HOST)
                    ?.id
                }
              </>
            </Text>
          </List.Item>
          <List.Item>
            <Text size="sm">
              <>
                Guest player ID:{" "}
                {
                  game.players.find((player) => player.type == PlayerType.GUEST)
                    ?.id
                }
              </>
            </Text>
          </List.Item>
        </List>
      </Stack>
    </AccordionLayout>
  );
};

export default GameInfo;
