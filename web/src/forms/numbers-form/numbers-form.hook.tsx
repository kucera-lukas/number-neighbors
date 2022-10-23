import getNumberValidator from "../../validators/number.validator";

import { useForm } from "@mantine/form";

import type { NumbersFormValues } from "./numbers-form.types";

const useNumbersForm = () => {
  return useForm<NumbersFormValues>({
    initialValues: {
      first: 0,
      second: 0,
      third: 0,
    },

    validate: {
      first: getNumberValidator("first"),
      second: getNumberValidator("second"),
      third: getNumberValidator("third"),
    },
  });
};

export default useNumbersForm;
