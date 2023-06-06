import type NumberSelectionType from "./number-selection.enum";
import type NumberType from "./number-type.enum";

export interface ClassifiedUserNumber {
  value: string;
  type: NumberSelectionType;
}

export interface ClassifiedOpponentNumber {
  value: string;
  type: NumberType;
}
