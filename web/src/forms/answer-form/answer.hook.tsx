import AnswerType from "../../types/answer-type.enum";

import { useForm } from "@mantine/form";

import type { AnswerFormValues } from "./answer.types";

const useAnswerForm = () => {
  return useForm<AnswerFormValues>({
    initialValues: {
      type: AnswerType.NO,
    },

    validate: {
      type: (value) => {
        if (![AnswerType.NO, AnswerType.YES].includes(value)) {
          return "Answer type is not valid";
        }
      },
    },
  });
};

export default useAnswerForm;
