import type { UseFormReturnType } from "@mantine/form";

export interface TakeTurnFormValues {
  // the select component used to input this value only works with strings
  number: string;
}

export type TakeTurnFormReturnType = UseFormReturnType<TakeTurnFormValues>;
