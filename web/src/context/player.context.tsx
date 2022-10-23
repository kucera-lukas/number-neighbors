import { createContext, useContext, useState } from "react";

import type Player from "../types/player.type";
import type { PropsWithChildren, Dispatch, SetStateAction } from "react";

export type PlayerPayload = readonly [
  Player | undefined,
  Dispatch<SetStateAction<Player | undefined>>,
];

export const PlayerContext = createContext<PlayerPayload | undefined>(
  undefined,
);

export type PlayerProviderProps = PropsWithChildren<Record<never, never>>;

export const PlayerProvider = ({
  children,
}: PlayerProviderProps): JSX.Element => {
  const [player, setPlayer] = useState<Player>();

  return (
    <PlayerContext.Provider value={[player, setPlayer]}>
      {children}
    </PlayerContext.Provider>
  );
};

export const usePlayer = (): PlayerPayload => {
  const context = useContext(PlayerContext);

  if (!context) {
    throw new Error("usePlayer must be used within a PlayerProvider");
  }

  return context;
};
