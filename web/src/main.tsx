import App from "./app";
import { GameProvider } from "./context/game.context";
import { PlayerProvider } from "./context/player.context";

import { MantineProvider } from "@mantine/core";
import * as ReactDOM from "react-dom/client";

const root = ReactDOM.createRoot(
  document.querySelector("#root") as HTMLElement,
);

root.render(
  <MantineProvider
    withGlobalStyles
    withNormalizeCSS
    theme={{ colorScheme: "dark" }}
  >
    <GameProvider>
      <PlayerProvider>
        <App />
      </PlayerProvider>
    </GameProvider>
  </MantineProvider>,
);
