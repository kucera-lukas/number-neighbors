import {
  MAX_NUMBER_VALUE,
  MIN_NUMBER_VALUE,
} from "../constants/number.constants";

import type { NumbersFormValues } from "../forms/numbers-form/numbers-form.types";

export const numberRangeValidator = (n: number): boolean =>
  n >= MIN_NUMBER_VALUE && n <= MAX_NUMBER_VALUE;

const getNumberUniqueValidator = (type: keyof NumbersFormValues) => {
  switch (type) {
    case "first": {
      return (value: number, values: NumbersFormValues): boolean =>
        ![values.second, values.third].some((num) => value == num);
    }
    case "second": {
      return (value: number, values: NumbersFormValues): boolean =>
        ![values.first, values.third].some((num) => value == num);
    }
    case "third": {
      return (value: number, values: NumbersFormValues): boolean =>
        ![values.first, values.second].some((num) => value == num);
    }
  }
};

const getNumberValidator = (type: keyof NumbersFormValues) => {
  const uniqueValidator = getNumberUniqueValidator(type);

  return (value: number, values: NumbersFormValues): string | undefined => {
    if (!numberRangeValidator(value)) {
      return `Number ${value} outside range`;
    } else if (!uniqueValidator(value, values)) {
      return `Number ${value} already used`;
    }

    return undefined;
  };
};

export default getNumberValidator;
