import GameAccordion from "../../components/game/game-accordion.component";
import { useGamePayload } from "../../context/game-payload.context";
import PageLayout from "../../layouts/page.layout";

import { useParams } from "react-router-dom";

const Game = (): JSX.Element => {
  const params = useParams();
  const gameId = params.gameId as string;
  const [gamePayload] = useGamePayload();
  const gameIdMatch = gamePayload?.id.toString() === gameId;

  return (
    <PageLayout title="game">{gameIdMatch && <GameAccordion />}</PageLayout>
  );
};

export default Game;
