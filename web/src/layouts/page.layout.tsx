import { SERVER_URI } from "../config/environment";
import { useGamePayload } from "../context/game-payload.context";
import useLocalStorageItem from "../hooks/localstorage.hook";

import { Center, Stack, Title, UnstyledButton } from "@mantine/core";
import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";

import type GamePayload from "../types/game-payload.type";
import type { PropsWithChildren } from "react";

const BASE_TITLE = "number-neighbors";

export type PageLayoutProps = PropsWithChildren<{
  title: string;
}>;

const PageLayout = ({ children, title }: PageLayoutProps): JSX.Element => {
  const navigate = useNavigate();
  const params = useParams();
  const [, setGamePayload] = useGamePayload();
  const gameId = params.gameId;
  const [token] = useLocalStorageItem<string>("token");

  useEffect(() => {
    if (!gameId || !token) {
      return;
    }

    void fetch(`${SERVER_URI}/games/${gameId}/payload`, {
      method: "GET",
      headers: {
        Authorization: token,
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        if (res.ok) {
          return res.json();
        }
      })
      .then((res: GamePayload) => {
        setGamePayload(res);
      });
  }, [gameId, token, setGamePayload]);

  useEffect(() => {
    document.title = `${BASE_TITLE} | ${title}`;
  }, [title]);

  return (
    <Center>
      <Stack>
        <UnstyledButton onClick={() => navigate("/")}>
          <Title>number-neighbors</Title>
        </UnstyledButton>
        {children}
      </Stack>
    </Center>
  );
};

export default PageLayout;
