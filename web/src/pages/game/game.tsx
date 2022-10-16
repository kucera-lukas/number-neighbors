import ErrorText from "../../components/errors/error.text";
import GameInfo from "../../components/game/game-info.component";
import GameInvite from "../../components/game/game-invite.component";
import Play from "../../components/game/play.component";
import { SERVER_URI } from "../../config/environment";
import { GameProvider } from "../../context/game.context";
import useLocalStorageItem from "../../hooks/localstorage.hook";
import PageLayout from "../../layouts/page.layout";

import { Stack } from "@mantine/core";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import type GameType from "../../types/game.type";

const Game = (): JSX.Element => {
  const params = useParams();
  const gameId = params.gameId as string;
  const [token] = useLocalStorageItem<string>("token");
  const [error, setError] = useState<string>();
  const [game, setGame] = useState<GameType>();

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
      .then((res: GameType) => {
        setGame(res);
        // eslint-disable-next-line unicorn/no-useless-undefined
        setError(undefined);
      })
      .catch((error: Error) => {
        setError(error.message);
      });
  }, [gameId, token]);

  return (
    <PageLayout title="game">
      {game && (
        <GameProvider game={game}>
          <Stack>
            <GameInfo />
            <GameInvite />
            <Play />
          </Stack>
        </GameProvider>
      )}
      {error && <ErrorText error={error} />}
    </PageLayout>
  );
};

export default Game;
