export const isPositiveInteger = (value: string): boolean => {
  return /^\d+$/.test(value);
};
