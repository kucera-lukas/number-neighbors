import React from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client/dist/sockjs";

const Sock = (): JSX.Element => {
  const auth =
    "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJnYW1lSWQiOjE2LCJzdWIiOiIyMCIsImF1ZCI6Im51bWJlcm5laWdoYm9ycyIsIm5iZiI6MTY2NTMwMjA0MywiaXNzIjoiaHR0cHM6XC9cL251bWJlcm5laWdoYm9ycy5sdWthc2t1Y2VyYS5jb20iLCJuYW1lIjoiNDY1NDY1IiwiaWF0IjoxNjY1MzAyMDQzLCJhdXRob3JpdGllcyI6InBsYXllciIsInBsYXllcklkIjoyMH0.gNURp98pcKSET74Xa-Pb8ZdqPTSLthP3um1KC0FSUo3Ye0ld_vkOxkaUKa-smXsWvs-NDqV-Ewf_4XSq8-4fzQeALEgpY3xwF7frzr6z15Rng-xbXJgq8UBfYE9KFbmUPCPERnERlX_6mN0jjhzYoZYszIGylRnUmH9TKE-49rhGouUF8I85s3WGDzFoRsNBkDnkR9Uu53cUvp6O_dkjs5e7jfSRcFC51ji6UhAs2O-jkdXH8g-Zl2Ejnjm4i72VoyClG5pLIHFsqNqfVr1P-O50elVw272ONVlOTUxBI7hbhGD9qHMHhR_HR4v2_-0nMeUdfoXxDqvluD3I2fNpQw";

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
