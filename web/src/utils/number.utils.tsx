import {
  MAX_NUMBER_VALUE,
  MIN_NUMBER_VALUE,
} from "../constants/number.constants";
import NumberSelectionType from "../types/number-selection.enum";
import NumberType from "../types/number-type.enum";

import type {
  ClassifiedUserNumber,
  ClassifiedOpponentNumber,
} from "../types/classified-number.type";
import type NumberPayload from "../types/number-payload.type";
import type ThemedNumber from "../types/themed-number.type";

export const reachableNumbers = (numbers: NumberPayload[]): number[] => {
  return numbers.flatMap((number) => {
    const result: number[] = [];

    if (number.guessed) {
      return result;
    }

    if (number.value > MIN_NUMBER_VALUE) {
      result.push(number.value - 1);
    }

    result.push(number.value);

    if (number.value < MAX_NUMBER_VALUE) {
      result.push(number.value + 1);
    }

    return result;
  });
};

export const classifyUserNumbers = (
  numbers: NumberPayload[],
): ClassifiedUserNumber[] => {
  const reachable = reachableNumbers(numbers);
  const numberMap = new Map(numbers.map((number) => [number.value, number]));

  return Array.from({ length: 16 }, (_, i) => {
    const number = numberMap.get(i);
    let type: NumberSelectionType;

    if (number?.guessed) {
      type = NumberSelectionType.GUESSED;
    } else if (number) {
      type = NumberSelectionType.CHOSEN;
    } else if (reachable.includes(i)) {
      type = NumberSelectionType.REACHABLE;
    } else {
      type = NumberSelectionType.UNREACHABLE;
    }

    return {
      value: i.toString(),
      type: type,
    };
  });
};

export const classifyOpponentNumbers = (
  numbers: NumberPayload[],
): ClassifiedOpponentNumber[] => {
  const numberMap = new Map(numbers.map((number) => [number.type, number]));

  return [NumberType.FIRST, NumberType.SECOND, NumberType.THIRD].map((type) => {
    const number = numberMap.get(type);
    const value = number ? number.value : "??";

    return {
      value: value.toString(),
      type: type,
    };
  });
};

export const toDiv = (number: ThemedNumber, key: number): JSX.Element => {
  return (
    <div
      style={{ backgroundColor: number.backgroundColor }}
      key={key}
    >
      <div style={{ color: number.color, textAlign: "center" }}>
        {number.value}
      </div>
    </div>
  );
};
