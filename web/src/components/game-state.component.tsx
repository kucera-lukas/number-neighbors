import useLocalStorageItem from "../hooks/localstorage.hook";
import useStompClient from "../hooks/stomp-client.hook";

const GameState = (): JSX.Element => {
  const [token] = useLocalStorageItem<string>("token");
  const stompClient = useStompClient("play", token, (client) => {
    client.subscribe("/user/queue/turns", (message) => {
      console.log("subscription: " + message.body);
    });
  });

  return (
    <div>
      <button
        title="HERE"
        onClick={() =>
          stompClient.publish({
            destination: "/app/games/16/turn",
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
