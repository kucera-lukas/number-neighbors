import { createContext, useContext, useState } from "react";

import type Game from "../types/game.type";
import type { PropsWithChildren, Dispatch, SetStateAction } from "react";

export type GamePayload = readonly [Game, Dispatch<SetStateAction<Game>>];

export const GameContext = createContext<GamePayload | undefined>(undefined);

export type GameProviderProps = PropsWithChildren<{ game: Game }>;

export const GameProvider = ({
  game: initialGame,
  children,
}: GameProviderProps): JSX.Element => {
  const [game, setGame] = useState<Game>(initialGame);

  return (
    <GameContext.Provider value={[game, setGame]}>
      {children}
    </GameContext.Provider>
  );
};

export const useGame = (): GamePayload => {
  const context = useContext(GameContext);

  if (!context) {
    throw new Error("useGame must be used within a GameProvider");
  }

  return context;
};
