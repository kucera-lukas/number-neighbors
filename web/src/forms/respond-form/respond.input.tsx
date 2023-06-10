import { useGamePayload } from "../../context/game-payload.context";
import { useTurns } from "../../context/turns.context";
import ResponseType from "../../types/response-type.enum";
import { requiresPassType } from "../../utils/response.utils";

import { SegmentedControl } from "@mantine/core";

import type ResponsePayload from "../../types/response-payload.type";
import type TurnPayload from "../../types/turn-payload.type";
import type { RespondFormReturnType } from "./respond.types";
import type { SegmentedControlItem } from "@mantine/core";

export type RespondInputProps = {
  form: RespondFormReturnType;
  disabled: boolean;
};

const BASE_DATA = [
  {
    label: "Pass",
    value: ResponseType.PASS,
  },
  {
    label: "Guess",
    value: ResponseType.GUESS,
  },
];

const createData = (
  playerResponses: ResponsePayload[],
): SegmentedControlItem[] => {
  return BASE_DATA.map((item) => {
    return { ...item, disabled: requiresPassType(item.value, playerResponses) };
  });
};

const RespondInput = ({ form, disabled }: RespondInputProps): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const [turns] = useTurns();

  const playerResponses = gamePayload
    ? turns
        .filter((turn) => turn.playerId !== gamePayload.user.id)
        .filter((turn): turn is Required<TurnPayload> => !!turn.response)
        .map((turn) => turn.response)
    : [];

  return (
    <SegmentedControl
      data={createData(playerResponses)}
      size="xs"
      disabled={disabled}
      {...form.getInputProps("type")}
    />
  );
};

export default RespondInput;
