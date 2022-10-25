import { createContext, useContext, useState } from "react";

import type { PropsWithChildren, Dispatch, SetStateAction } from "react";

export type AccordionValuesPayload<Values extends string[] = string[]> =
  readonly [Values, Dispatch<SetStateAction<Values>>];

export const AccordionValuesContext = createContext<
  AccordionValuesPayload | undefined
>(undefined);

export type AccordionValuesProviderProps = PropsWithChildren<
  Record<never, never>
>;

export const AccordionValuesProvider = ({
  children,
}: AccordionValuesProviderProps): JSX.Element => {
  const [values, setValues] = useState<string[]>([]);

  return (
    <AccordionValuesContext.Provider value={[values, setValues]}>
      {children}
    </AccordionValuesContext.Provider>
  );
};

export const useAccordionValues = (): AccordionValuesPayload => {
  const context = useContext(AccordionValuesContext);

  if (!context) {
    throw new Error(
      "useAccordionValues must be used within an AccordionValuesProvider",
    );
  }

  return context;
};
