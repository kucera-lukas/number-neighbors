import { SERVER_URI } from "../config/environment";

import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client/dist/sockjs";

const Sock = (): JSX.Element => {
  const auth = "";

  const stompClient = new Client({
    connectHeaders: {
      Authorization: auth,
    },
  });

  stompClient.configure({
    webSocketFactory: () => new SockJS(`${SERVER_URI}/play`),
    onConnect: (): void => {
      stompClient.subscribe("/user/queue/turns", (message) => {
        console.log("subscription: " + message.body);
      });
    },
  });

  stompClient.activate();

  return (
    <div>
      <button
        title="HERE"
        onClick={() =>
          stompClient.publish({
            destination: "/app/games/16/turn",
            body: "test",
            headers: {
              Authorization: auth,
            },
          })
        }
      >
        HERE
      </button>
    </div>
  );
};

export default Sock;
