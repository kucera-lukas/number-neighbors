import React from "react";
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
    webSocketFactory: () => new SockJS("http://localhost:8080/play"),
    onConnect: (receipt) => {
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
        children="HERE"
        onClick={() =>
          stompClient.publish({
            destination: "/app/games/16/turn",
            body: "test",
            headers: {
              Authorization: auth,
            },
          })
        }
      ></button>
    </div>
  );
};

export default Sock;
