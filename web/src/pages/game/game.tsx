import ErrorText from "../../components/errors/error.text";
import GameState from "../../components/game-state.component";
import { SERVER_URI } from "../../config/environment";
import useLocalStorageItem from "../../hooks/localstorage.hook";
import PageLayout from "../../layouts/page.layout";

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const Game = (): JSX.Element => {
  const params = useParams();
  const gameId = params.gameId as string;
  const [token] = useLocalStorageItem<string>("token");
  const [error, setError] = useState<string>();
  const [authenticated, setAuthenticated] = useState(false);

  useEffect(() => {
    // eslint-disable-next-line unicorn/no-useless-undefined
    setError(undefined);

    fetch(`${SERVER_URI}/games/${gameId}`, {
      method: "GET",
      headers: {
        Authorization: token,
        "Content-Type": "application/json",
      },
      credentials: "omit",
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(res.status.toLocaleString());
        }
      })
      .then(() => {
        // eslint-disable-next-line unicorn/no-useless-undefined
        setError(undefined);
        setAuthenticated(true);
      })
      .catch((error: Error) => {
        setError(error.message);
        setAuthenticated(false);
      });
  }, [gameId, token]);

  return (
    <PageLayout title="game">
      {authenticated ? <GameState /> : "Not authenticated"}
      {error && <ErrorText error={error} />}
    </PageLayout>
  );
};

export default Game;
