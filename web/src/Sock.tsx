import React from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client/dist/sockjs";

const Sock = (): JSX.Element => {
  const auth =
    "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJnYW1lSWQiOjE1LCJzdWIiOiI0NjU0NjUiLCJhdWQiOiJudW1iZXJuZWlnaGJvcnMiLCJuYmYiOjE2NjUyMzc1ODMsImlzcyI6Imh0dHBzOlwvXC9udW1iZXJuZWlnaGJvcnMubHVrYXNrdWNlcmEuY29tIiwiaWF0IjoxNjY1MjM3NTgzLCJhdXRob3JpdGllcyI6InBsYXllciIsInBsYXllcklkIjoxOH0.Ro2r93zXvRLbZxbyjD8wyv3ATvpPfd2piQVFcXJwYK9LDA9uz5ANY0d4bCBzrHAG8xaxg-Hie6iVcbnvHxJjzj60jCqVq9WrFU8UfaKh9Yz0Vld8yoi2wHY0xEGK-zacnlgGgqJvFMysKpe_OSDFonOMUoVIQv7-sdTJmYqPStmPd7EIyoajunpeqPgaVVnxxpA0HJrwpT23aTnHhWUl5Q9mhzEUntD5CjoXM9Rp6Nv0o8zQZ5ZlYIMdmfYUb6F_CV-89R-IxyIVSDD5zumEO3yzwbMaEIBW4owA-gImS5EwQ-sEY6KDw4P0QwoHpg_Rm0KmyQYCTmGkZYQk8qIn5g";

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
            destination: "/app/games/15/turn",
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
