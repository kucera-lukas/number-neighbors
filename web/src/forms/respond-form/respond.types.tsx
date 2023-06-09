import type ResponseType from "../../types/response-type.enum";
import type { UseFormReturnType } from "@mantine/form";

export interface RespondFormValues {
  type: ResponseType;
}

export type RespondFormReturnType = UseFormReturnType<RespondFormValues>;
