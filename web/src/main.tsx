import App from "./app";
import { GamePayloadProvider } from "./context/game-payload.context";
import { TurnsProvider } from "./context/turns.context";

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
    <GamePayloadProvider>
      <TurnsProvider>
        <App />
      </TurnsProvider>
    </GamePayloadProvider>
  </MantineProvider>,
);
