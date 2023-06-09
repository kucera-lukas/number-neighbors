import ResponseType from "../../types/response-type.enum";

import { SegmentedControl } from "@mantine/core";

import type { RespondFormReturnType } from "./respond.types";

export type RespondInputProps = {
  form: RespondFormReturnType;
  disabled: boolean;
};

const DATA = [
  {
    label: "Pass",
    value: ResponseType.PASS,
  },
  {
    label: "Guess",
    value: ResponseType.GUESS,
  },
];

const RespondInput = ({ form, disabled }: RespondInputProps): JSX.Element => {
  return (
    <SegmentedControl
      data={DATA}
      size="xs"
      disabled={disabled}
      {...form.getInputProps("type")}
    />
  );
};

export default RespondInput;
