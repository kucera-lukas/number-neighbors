import ErrorText from "../../components/errors/error.text";
import GameAccordion from "../../components/game/game-accordion.component";
import { SERVER_URI } from "../../config/environment";
import { useGamePayload } from "../../context/game-payload.context";
import useLocalStorageItem from "../../hooks/localstorage.hook";
import PageLayout from "../../layouts/page.layout";

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import type GamePayload from "../../types/game-payload.type";

const Game = (): JSX.Element => {
  const params = useParams();
  const gameId = params.gameId as string;
  const [token] = useLocalStorageItem<string>("token");
  const [error, setError] = useState<string>();
  const [gamePayload, setGamePayload] = useGamePayload();
  const gameIdMatch = gamePayload?.id.toString() === gameId;

  useEffect(() => {
    // eslint-disable-next-line unicorn/no-useless-undefined
    setError(undefined);

    fetch(`${SERVER_URI}/games/${gameId}`, {
      method: "GET",
      headers: {
        Authorization: token,
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(res.status.toLocaleString());
        }

        return res.json();
      })
      .then((res: GamePayload) => {
        setGamePayload(res);
      })
      .catch((error: Error) => {
        setError(error.message);
      });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [gameId, token]);

  return (
    <PageLayout title="game">
      {gameIdMatch && <GameAccordion />}
      {error && <ErrorText error={error} />}
    </PageLayout>
  );
};

export default Game;
