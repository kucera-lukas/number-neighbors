import App from "./app";

import * as ReactDOM from "react-dom/client";

const root = ReactDOM.createRoot(
  document.querySelector("#root") as HTMLElement,
);

root.render(<App />);
