import { createContext, useContext, useState } from "react";

import type GamePayload from "../types/game-payload.type";
import type { PropsWithChildren, Dispatch, SetStateAction } from "react";

export type GamePayloadType = readonly [
  GamePayload | undefined,
  Dispatch<SetStateAction<GamePayload | undefined>>,
];

export const GamePayloadContext = createContext<GamePayloadType | undefined>(
  undefined,
);

export type GamePayloadProviderProps = PropsWithChildren<Record<never, never>>;

export const GamePayloadProvider = ({
  children,
}: GamePayloadProviderProps): JSX.Element => {
  const [gamePayload, setGamePayload] = useState<GamePayload>();

  return (
    <GamePayloadContext.Provider value={[gamePayload, setGamePayload]}>
      {children}
    </GamePayloadContext.Provider>
  );
};

export const useGamePayload = (): GamePayloadType => {
  const context = useContext(GamePayloadContext);

  if (!context) {
    throw new Error("useGamePayload must be used within a GamePayloadProvider");
  }

  return context;
};
