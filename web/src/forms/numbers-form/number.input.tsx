import {
  MIN_NUMBER_VALUE,
  MAX_NUMBER_VALUE,
} from "../../constants/number.constants";

import { NumberInput as MantineNumberInput } from "@mantine/core";

import type {
  NumbersFormFormReturnType,
  NumbersFormValues,
} from "./numbers-form.types";

export type NumberInputProps = {
  form: NumbersFormFormReturnType;
  n: number;
  type: keyof NumbersFormValues;
  disabled: boolean;
};

const NumberInput = ({
  form,
  n,
  type,
  disabled,
}: NumberInputProps): JSX.Element => {
  return (
    <MantineNumberInput
      label={`Number ${n}`}
      min={MIN_NUMBER_VALUE}
      max={MAX_NUMBER_VALUE}
      withAsterisk
      noClampOnBlur
      disabled={disabled}
      {...form.getInputProps(type)}
    />
  );
};

export default NumberInput;
