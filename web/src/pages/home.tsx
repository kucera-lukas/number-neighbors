import ErrorText from "../components/errors/error.text";
import Sock from "../components/sock";
import { SERVER_URI } from "../config/environment";
import LocalStorageService from "../services/local-storage.service";

import { Button, Center, Stack, TextInput, Title } from "@mantine/core";
import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";

import type Game from "../types/game.type";

type NewGameResponse = {
  game: Game;
  token: string;
};

const Home = (): JSX.Element => {
  const navigate = useNavigate();
  const [name, setName] = useState<string>();
  const [error, setError] = useState<string>();

  const onClick = useCallback(() => {
    if (!name) {
      setError("Name is required");
    } else {
      fetch(`${SERVER_URI}/games`, {
        method: "POST",
        body: JSON.stringify({ hostName: name }),
        headers: { "Content-Type": "application/json" },
      })
        .then((res) => {
          if (!res.ok) {
            throw new Error(res.status.toLocaleString());
          }
          return res.json();
        })
        .then((res: NewGameResponse) => {
          LocalStorageService.set("token", res.token);
          navigate(`/game/${res.game.id}`);
        })
        .catch((error: Error) => setError(error.message));
    }
  }, [name, navigate]);

  return (
    <Center>
      <Stack>
        <Title>number-neighbors</Title>
        <TextInput
          placeholder="Your name"
          label="Player name"
          withAsterisk
          value={name}
          onChange={(event) => setName(event.currentTarget.value)}
        />
        <Button onClick={onClick}>New game</Button>
        {error && <ErrorText error={error} />}
        <Sock />
      </Stack>
    </Center>
  );
};

export default Home;
