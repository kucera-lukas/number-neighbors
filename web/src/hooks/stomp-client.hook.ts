import useLocalStorageItem from "./localstorage.hook";

import { SERVER_URI } from "../config/environment";
import { useGamePayload } from "../context/game-payload.context";
import { useTurns } from "../context/turns.context";

import { Client } from "@stomp/stompjs";
import { useCallback, useEffect, useState } from "react";
import SockJS from "sockjs-client/dist/sockjs";

import type GamePayload from "../types/game-payload.type";
import type TurnPayload from "../types/turn-payload.type";
import type { IMessage } from "@stomp/stompjs";

const STOMP_ENDPOINT = "play";
const PAYLOAD_DESTINATION = "/user/queue/payload";
const TURNS_DESTINATION = "/user/queue/turns";

const useStompClient = (): Client | undefined => {
  const [, setGamePayload] = useGamePayload();
  const [, setTurns] = useTurns();
  const [token] = useLocalStorageItem<string>("token");
  const [client, setClient] = useState<Client>();

  const onPayload = useCallback(
    (message: IMessage) => {
      setGamePayload(JSON.parse(message.body) as GamePayload);
    },
    [setGamePayload],
  );

  const onTurn = useCallback(
    (message: IMessage) => {
      const turn = JSON.parse(message.body) as TurnPayload;

      setTurns((turns) => {
        const newTurns = [...turns];

        if (turn.id === newTurns.at(-1)?.id) {
          newTurns[newTurns.length - 1] = turn;
        } else {
          newTurns.push(turn);
        }

        return newTurns;
      });
    },
    [setTurns],
  );

  useEffect(() => {
    if (!token) {
      return;
    }

    const client = createClient(STOMP_ENDPOINT, token, (client) => {
      client.subscribe(PAYLOAD_DESTINATION, onPayload);
      client.subscribe(TURNS_DESTINATION, onTurn);
    });

    setClient(client);
  }, [token, onPayload, onTurn]);

  return client;
};

const createClient = (
  endpoint: string,
  authorization: string,
  onConnect: (stompClient: Client) => void,
): Client => {
  const stompClient = new Client({
    connectHeaders: {
      Authorization: authorization,
    },
  });

  stompClient.configure({
    webSocketFactory: () => new SockJS(`${SERVER_URI}/${endpoint}`),
    onConnect: () => onConnect(stompClient),
  });

  stompClient.activate();

  return stompClient;
};

export default useStompClient;
