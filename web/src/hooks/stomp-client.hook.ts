import { SERVER_URI } from "../config/environment";

import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client/dist/sockjs";

const useStompClient = (
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
