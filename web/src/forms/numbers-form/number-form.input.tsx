import NumberInput from "./number.input";

import {
  MAX_NUMBER_VALUE,
  MIN_NUMBER_VALUE,
} from "../../constants/number.constants";

import { Stack } from "@mantine/core";

import type {
  NumberInputDescriptor,
  NumbersFormFormReturnType,
} from "./numbers-form.types";

export type NumbersFormInputProps = {
  form: NumbersFormFormReturnType;
  disabled: boolean;
};

const DESCRIPTORS: NumberInputDescriptor[] = [
  { n: 1, type: "first" },
  { n: 2, type: "second" },
  { n: 3, type: "third" },
];

const NumbersFormInput = ({
  form,
  disabled,
}: NumbersFormInputProps): JSX.Element => {
  return (
    <Stack
      spacing="xs"
      title={`Between ${MIN_NUMBER_VALUE} to ${MAX_NUMBER_VALUE}`}
    >
      {DESCRIPTORS.map(({ n, type }) => (
        <NumberInput
          key={n}
          form={form}
          n={n}
          type={type}
          disabled={disabled}
        />
      ))}
    </Stack>
  );
};

export default NumbersFormInput;
