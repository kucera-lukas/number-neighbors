import { useGamePayload } from "../../context/game-payload.context";
import NumberSelectionType from "../../types/number-selection.enum";
import { classifyUserNumbers } from "../../utils/number.utils";

import { Select } from "@mantine/core";

import type NumberPayload from "../../types/number-payload.type";
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
  userNumbers: NumberPayload[],
): readonly SelectItem[] => {
  const classifiedNumbers = classifyUserNumbers(userNumbers ?? []);

  return classifiedNumbers
    .filter((classifiedNumber) => SELECTABLE_NUMBERS.has(classifiedNumber.type))
    .map((classifiedNumber) => {
      return {
        value: classifiedNumber.value,
        label: classifiedNumber.value,
        group: classifiedNumber.type.toLowerCase(),
      };
    });
};

const TakeTurnInput = ({ form, disabled }: TakeTurnInputProps): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const data = createSelectData(gamePayload?.user.numbers ?? []);

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
