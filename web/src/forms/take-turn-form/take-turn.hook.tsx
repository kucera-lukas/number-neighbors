import { useForm } from "@mantine/form";

import type { TakeTurnFormValues } from "./take-turn.types";

const useTakeTurnForm = () => {
  return useForm<TakeTurnFormValues>({
    initialValues: {
      number: "",
    },

    validate: {
      number: (value) => {
        if (!value) {
          return "Number is not valid";
        }
      },
    },
  });
};

export default useTakeTurnForm;
