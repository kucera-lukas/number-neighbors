import App from "./app";

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
    <App />
  </MantineProvider>,
);
