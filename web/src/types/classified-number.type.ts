import type NumberSelectionType from "./number-selection.enum";

export interface ClassifiedUserNumber {
  value: string;
  type: NumberSelectionType;
}

export interface ClassifiedOpponentNumber {
  value: string;
  guessed: boolean;
}
