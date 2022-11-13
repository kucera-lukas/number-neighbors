import ErrorText from "../../components/errors/error.text";
import ChooseNumbers from "../../components/game/choose-numbers.component";
import GameInfo from "../../components/game/game-info.component";
import GameInvite from "../../components/game/game-invite.component";
import Play from "../../components/game/play.component";
import { SERVER_URI } from "../../config/environment";
import { useGamePayload } from "../../context/game-payload.context";
import useLocalStorageItem from "../../hooks/localstorage.hook";
import AccordionLayout from "../../layouts/accordion.layout";
import PageLayout from "../../layouts/page.layout";

import { useEffect, useMemo, useState } from "react";
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

    fetch(`${SERVER_URI}/games/payload`, {
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
  }, [gameId, gameIdMatch, token]);

  // we don't want to reset the state of the accordion values
  // during each re-render
  const accordionLayout = useMemo(() => {
    return (
      <AccordionLayout defaultValues={["game-info"]}>
        <GameInfo />
        <GameInvite />
        <ChooseNumbers />
        <Play />
      </AccordionLayout>
    );
  }, []);

  return (
    <PageLayout title="game">
      {gameIdMatch && accordionLayout}
      {error && <ErrorText error={error} />}
    </PageLayout>
  );
};

export default Game;
