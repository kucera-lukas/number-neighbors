import { useGamePayload } from "../../context/game-payload.context";
import { useTurns } from "../../context/turns.context";
import NumberSelectionType from "../../types/number-selection.enum";
import { classifyUserNumbers } from "../../utils/number.utils";
import { requiresChosenNumber } from "../../utils/turn.utils";

import { Select } from "@mantine/core";

import type GamePayload from "../../types/game-payload.type";
import type TurnPayload from "../../types/turn-payload.type";
import type { TakeTurnFormReturnType } from "./take-turn.types";
import type { SelectItem } from "@mantine/core";

const SELECTABLE_NUMBERS = new Set([
  NumberSelectionType.CHOSEN,
  NumberSelectionType.REACHABLE,
]);

export type TakeTurnInputProps = {
  form: TakeTurnFormReturnType;
  disabled: boolean;
};

const createSelectData = (
  gamePayload: GamePayload,
  turns: TurnPayload[],
): readonly SelectItem[] => {
  const classifiedNumbers = classifyUserNumbers(
    gamePayload?.user.numbers ?? [],
  );
  const playerNumbers = turns.filter(
    (turn) => turn.playerId === gamePayload.user.id,
  );
  const disableReachableNumbers = requiresChosenNumber(
    gamePayload.user,
    playerNumbers,
  );

  return classifiedNumbers
    .filter((classifiedNumber) => SELECTABLE_NUMBERS.has(classifiedNumber.type))
    .map((classifiedNumber) => {
      return {
        value: classifiedNumber.value,
        label: classifiedNumber.value,
        group: classifiedNumber.type.toLowerCase(),
        disabled:
          classifiedNumber.type === NumberSelectionType.REACHABLE &&
          disableReachableNumbers,
      };
    });
};

const TakeTurnInput = ({ form, disabled }: TakeTurnInputProps): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const [turns] = useTurns();
  const data = gamePayload ? createSelectData(gamePayload, turns) : [];

  return (
    <Select
      data={data}
      clearable
      allowDeselect
      label="Select your number"
      placeholder="Select number"
      withAsterisk
      required
      size="xs"
      maxDropdownHeight={200}
      dropdownPosition="top"
      disabled={disabled}
      {...form.getInputProps("number")}
    />
  );
};

export default TakeTurnInput;
