import { useGame } from "../../context/game.context";
import useLocalStorageItem from "../../hooks/localstorage.hook";
import useStompClient from "../../hooks/stomp-client.hook";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { Stack } from "@mantine/core";

import type Game from "../../types/game.type";

const Play = (): JSX.Element => {
  const [game, setGame] = useGame();
  const [token] = useLocalStorageItem<string>("token");
  const stompClient = useStompClient("play", token, (client) => {
    client.subscribe("/user/queue/turns", (message) => {
      setGame(JSON.parse(message.body) as Game);
      console.log("game:", JSON.parse(message.body));
    });
  });
  const disabled =
    !game ||
    game.players.length !== 2 ||
    game.players.some((player) => player.numbers.length === 0);

  return (
    <AccordionItemLayout
      title="Play"
      value="play"
      disabled={disabled}
    >
      <Stack spacing="xs">
        <button onClick={() => stompClient.activate()}>Start</button>
        <button
          onClick={() => {
            if (!game) {
              return;
            }

            stompClient.publish({
              destination: `/app/games/${game.id}/turn`,
              body: "test",
              headers: {
                Authorization: token,
              },
            });
          }}
        >
          Turn
        </button>
      </Stack>
    </AccordionItemLayout>
  );
};

export default Play;
