import { useGamePayload } from "../../context/game-payload.context";
import { useTurns } from "../../context/turns.context";
import useLocalStorageItem from "../../hooks/localstorage.hook";
import useStompClient from "../../hooks/stomp-client.hook";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { List } from "@mantine/core";

import type GamePayload from "../../types/game-payload.type";
import type TurnPayload from "../../types/turn-payload.type";
import type { IMessage } from "@stomp/stompjs";

const STOMP_ENDPOINT = "play";
const PAYLOAD_DESTINATION = "/user/queue/payload";
const TURNS_DESTINATION = "/user/queue/turns";

const Play = (): JSX.Element => {
  const [gamePayload, setGamePayload] = useGamePayload();
  const [turns, setTurns] = useTurns();
  const [token] = useLocalStorageItem<string>("token");
  const disabled = !gamePayload?.ready;

  const onPayload = (message: IMessage) =>
    setGamePayload(JSON.parse(message.body) as GamePayload);

  const onTurn = (message: IMessage) => {
    const turn = JSON.parse(message.body) as TurnPayload;

    setTurns((turns) => {
      if (turns === undefined) {
        turns = [turn];
      } else if (turn.id === turns.at(-1)?.id) {
        turns[turns.length - 1] = turn;
      } else {
        turns.push(turn);
      }

      return turns;
    });
  };

  useStompClient(STOMP_ENDPOINT, token, (client) => {
    client.subscribe(PAYLOAD_DESTINATION, onPayload);
    client.subscribe(TURNS_DESTINATION, onTurn);
  });

  return (
    <AccordionItemLayout
      title="Play"
      value="play"
      disabled={disabled}
    >
      <List>
        {turns?.map((turn) => (
          <List.Item key={turn.id.toString()}>
            <>Turn: {turn.id}</>
          </List.Item>
        ))}
      </List>
    </AccordionItemLayout>
  );
};

export default Play;
