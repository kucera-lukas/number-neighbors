import { useGamePayload } from "../../context/game-payload.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { Button } from "@mantine/core";
import { useCallback } from "react";
import { useNavigate, useParams } from "react-router-dom";

const GamePlay = (): JSX.Element => {
  const navigate = useNavigate();
  const params = useParams();
  const gameId = params.gameId as string;
  const [gamePayload] = useGamePayload();
  const disabled = !gamePayload?.ready;

  const onPlay = useCallback(() => {
    navigate(`/game/${gameId}/play`);
  }, [gameId, navigate]);

  return (
    <AccordionItemLayout
      title="Play"
      value="play"
      disabled={disabled}
    >
      <Button
        onClick={onPlay}
        disabled={disabled}
        style={{ alignSelf: "flex-end" }}
      >
        Start
      </Button>
    </AccordionItemLayout>
  );
};

export default GamePlay;
