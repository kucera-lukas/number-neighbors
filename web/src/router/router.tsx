import ROUTES from "./routes";

import Game from "../pages/game/game";
import Invite from "../pages/game/invite";
import Home from "../pages/home";

import { createBrowserRouter, RouterProvider } from "react-router-dom";

const Router = (): JSX.Element => {
  const router = createBrowserRouter([
    {
      path: ROUTES.ROOT,
      element: <Home />,
    },
    {
      path: ROUTES.GAME,
      element: <Game />,
    },
    {
      path: ROUTES.INVITE,
      element: <Invite />,
    },
  ]);

  return <RouterProvider router={router} />;
};

export default Router;
