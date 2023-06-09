import ResponseType from "../../types/response-type.enum";

import { useForm } from "@mantine/form";

import type { RespondFormValues } from "./respond.types";

const useRespondForm = () => {
  return useForm<RespondFormValues>({
    initialValues: {
      type: ResponseType.PASS,
    },

    validate: {
      type: (value) => {
        if (![ResponseType.PASS, ResponseType.GUESS].includes(value)) {
          return "Response type is not valid";
        }
      },
    },
  });
};

export default useRespondForm;
