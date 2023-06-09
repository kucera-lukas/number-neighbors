import type AnswerType from "../../types/answer-type.enum";
import type { UseFormReturnType } from "@mantine/form";

export interface AnswerFormValues {
  type: AnswerType;
}

export type AnswerFormReturnType = UseFormReturnType<AnswerFormValues>;
