import { useGame } from "../context/game.context";
import useLocalStorageItem from "../hooks/localstorage.hook";
import useStompClient from "../hooks/stomp-client.hook";

import { Text } from "@mantine/core";
import { Link } from "react-router-dom";

import type Game from "../types/game.type";

const GameState = (): JSX.Element => {
  const [game, setGame] = useGame();
  const [token] = useLocalStorageItem<string>("token");
  const stompClient = useStompClient("play", token, (client) => {
    client.subscribe("/user/queue/turns", (message) => {
      setGame(JSON.parse(message.body) as Game);
    });
  });

  return (
    <div>
      <Text>
        <Link to="invite">
          <>Invite player to game {game.id}</>
        </Link>
      </Text>
      <button
        onClick={() =>
          stompClient.publish({
            destination: `/app/games/${game.id}/turn`,
            body: "test",
            headers: {
              Authorization: token,
            },
          })
        }
      >
        HERE
      </button>
    </div>
  );
};

export default GameState;
