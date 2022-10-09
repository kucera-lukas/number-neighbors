import App from "./app";

import { MantineProvider } from "@mantine/core";
import * as ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";

const root = ReactDOM.createRoot(
  document.querySelector("#root") as HTMLElement,
);

root.render(
  <MantineProvider
    withGlobalStyles
    withNormalizeCSS
    theme={{ colorScheme: "dark" }}
  >
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </MantineProvider>,
);
