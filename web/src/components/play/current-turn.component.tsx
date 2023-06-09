import { useGamePayload } from "../../context/game-payload.context";
import { useTurns } from "../../context/turns.context";
import TakeTurnForm from "../../forms/take-turn-form/take-turn.form";
import AccordionItemLayout from "../../layouts/accordion-item.layout";
import PlayerType from "../../types/player-type.enum";
import ResponseType from "../../types/response-type.enum";
import {
  answerStepProps,
  respondStepProps,
  takeTurnStepProps,
} from "../../utils/steps.utils";

import { Button, Stepper } from "@mantine/core";
import { useEffect, useState } from "react";

import type GamePayload from "../../types/game-payload.type";
import type TurnPayload from "../../types/turn-payload.type";
import type { Dispatch, SetStateAction } from "react";

export type CurrentTurnProps = {
  turnIndex: number;
  setTurnIndex: Dispatch<SetStateAction<number>>;
};

const isTurnOwner = (
  gamePayload: GamePayload | undefined,
  currentTurn: TurnPayload | undefined,
  previousTurn: TurnPayload | undefined,
): boolean => {
  return (
    // first turn is done by the game host
    (currentTurn === undefined &&
      previousTurn === undefined &&
      gamePayload?.user.type === PlayerType.HOST) ||
    // we don't know the current turn
    // so we infer it from the previous turn
    (currentTurn === undefined &&
      previousTurn !== undefined &&
      gamePayload?.user.id !== previousTurn.playerId) ||
    // we know the current turn
    (currentTurn !== undefined &&
      gamePayload?.user.id === currentTurn?.playerId)
  );
};

const CurrentTurn = ({
  turnIndex,
  setTurnIndex,
}: CurrentTurnProps): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const [turns] = useTurns();
  const currentTurn = turns.at(turnIndex);
  const previousTurn = turns.at(turnIndex - 1);
  const turnOwner = isTurnOwner(gamePayload, currentTurn, previousTurn);
  const [active, setActive] = useState(0);

  useEffect(() => {
    if (!currentTurn) {
      setActive(0);
    } else if (!currentTurn.response) {
      setActive(1);
      // eslint-disable-next-line unicorn/no-negated-condition
    } else if (!currentTurn.response.answer) {
      setActive(2);
    } else {
      setActive(3);
    }
  }, [currentTurn, setActive]);

  return (
    <AccordionItemLayout
      title="Current Turn"
      value="current-turn"
    >
      <Stepper
        active={active}
        onStepClick={setActive}
        allowNextStepsSelect={false}
        orientation="vertical"
        iconSize={30}
        size="xs"
        color="green"
      >
        <Stepper.Step {...takeTurnStepProps(currentTurn, turnOwner)}>
          {turnOwner && !currentTurn && <TakeTurnForm />}
        </Stepper.Step>
        <Stepper.Step {...respondStepProps(currentTurn, turnOwner)}>
          <div>take turn content</div>
        </Stepper.Step>
        {currentTurn?.response?.type === ResponseType.GUESS && (
          <Stepper.Step {...answerStepProps(currentTurn, turnOwner)}>
            <div>take turn content</div>
          </Stepper.Step>
        )}

        <Stepper.Completed>
          <Button
            onClick={() => setTurnIndex(turns.length)}
            disabled={!currentTurn?.complete}
            size="xs"
          >
            Next Turn
          </Button>
        </Stepper.Completed>
      </Stepper>
    </AccordionItemLayout>
  );
};

export default CurrentTurn;
