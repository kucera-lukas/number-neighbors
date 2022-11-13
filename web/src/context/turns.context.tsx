import { createContext, useContext, useState } from "react";

import type TurnPayload from "../types/turn-payload.type";
import type { PropsWithChildren, Dispatch, SetStateAction } from "react";

export type TurnsPayload = readonly [
  TurnPayload[] | undefined,
  Dispatch<SetStateAction<TurnPayload[] | undefined>>,
];

export const TurnsContext = createContext<TurnsPayload | undefined>(undefined);

export type TurnsProviderProps = PropsWithChildren<Record<never, never>>;

export const TurnsProvider = ({
  children,
}: TurnsProviderProps): JSX.Element => {
  const [turns, setTurns] = useState<TurnPayload[]>();

  return (
    <TurnsContext.Provider value={[turns, setTurns]}>
      {children}
    </TurnsContext.Provider>
  );
};

export const useTurns = (): TurnsPayload => {
  const context = useContext(TurnsContext);

  if (!context) {
    throw new Error("useTurns must be used within a TurnsProvider");
  }

  return context;
};
