import type { UseFormReturnType } from "@mantine/form";

export interface NumbersFormValues {
  first: number;
  second: number;
  third: number;
}

export type NumbersFormFormReturnType = UseFormReturnType<NumbersFormValues>;

export type NumberInputDescriptor = {
  n: 1 | 2 | 3;
  type: keyof NumbersFormValues;
};
