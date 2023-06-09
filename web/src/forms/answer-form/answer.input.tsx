import AnswerType from "../../types/answer-type.enum";

import { SegmentedControl } from "@mantine/core";

import type { AnswerFormReturnType } from "./answer.types";

export type AnswerInputProps = {
  form: AnswerFormReturnType;
  disabled: boolean;
};

const DATA = [
  {
    label: "Yes",
    value: AnswerType.YES,
  },
  {
    label: "No",
    value: AnswerType.NO,
  },
];

const AnswerInput = ({ form, disabled }: AnswerInputProps): JSX.Element => {
  return (
    <SegmentedControl
      data={DATA}
      size="xs"
      disabled={disabled}
      {...form.getInputProps("type")}
    />
  );
};

export default AnswerInput;
