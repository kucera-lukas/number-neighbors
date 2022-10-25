import ErrorText from "../../components/errors/error.text";
import ChooseNumbers from "../../components/game/choose-numbers.component";
import GameInfo from "../../components/game/game-info.component";
import GameInvite from "../../components/game/game-invite.component";
import Play from "../../components/game/play.component";
import { SERVER_URI } from "../../config/environment";
import { useGame } from "../../context/game.context";
import { usePlayer } from "../../context/player.context";
import useLocalStorageItem from "../../hooks/localstorage.hook";
import AccordionLayout from "../../layouts/accordion.layout";
import PageLayout from "../../layouts/page.layout";

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import type GameType from "../../types/game.type";
import type Player from "../../types/player.type";

const Game = (): JSX.Element => {
  const params = useParams();
  const gameId = params.gameId as string;
  const [token] = useLocalStorageItem<string>("token");
  const [error, setError] = useState<string>();
  const [game, setGame] = useGame();
  const [player, setPlayer] = usePlayer();
  const gameIdMatch = game?.id.toString() === gameId;

  useEffect(() => {
    if (gameIdMatch) {
      return;
    }

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
  }, [gameId, gameIdMatch, setGame, token]);

  useEffect(() => {
    // eslint-disable-next-line unicorn/no-useless-undefined
    setError(undefined);

    fetch(`${SERVER_URI}/players/me`, {
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
      .then((res: Player) => {
        setPlayer(res);
        // eslint-disable-next-line unicorn/no-useless-undefined
        setError(undefined);
      })
      .catch((error: Error) => {
        setError(error.message);
      });
  }, [setPlayer, token]);

  return (
    <PageLayout title="game">
      {gameIdMatch && player && (
        <AccordionLayout defaultValues={["game-info"]}>
          <GameInfo />
          <GameInvite />
          <ChooseNumbers />
          <Play />
        </AccordionLayout>
      )}

      {error && <ErrorText error={error} />}
    </PageLayout>
  );
};

export default Game;
