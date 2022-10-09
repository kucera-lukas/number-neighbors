import ErrorText from "../../components/errors/error.text";
import { SERVER_URI } from "../../config/environment";
import LocalStorageService from "../../services/local-storage.service";

import { Button, Center, Stack, TextInput, Title } from "@mantine/core";
import { useCallback, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import type Player from "../../types/player.type";

type NewPlayerResponse = {
  player: Player;
  token: string;
};

const Invite = (): JSX.Element => {
  const navigate = useNavigate();
  const params = useParams();
  const gameId = params.gameId as string;
  const [name, setName] = useState<string>();
  const [error, setError] = useState<string>();

  const onClick = useCallback(() => {
    if (!name) {
      setError("Name is required");
    } else {
      fetch(`${SERVER_URI}/players?game=${gameId}`, {
        method: "POST",
        body: JSON.stringify({ name: name }),
        headers: { "Content-Type": "application/json" },
      })
        .then((res) => {
          if (!res.ok) {
            throw new Error(res.status.toLocaleString());
          }
          return res.json();
        })
        .then((res: NewPlayerResponse) => {
          LocalStorageService.set("token", res.token);
          navigate(`/game/${gameId}`);
        })
        .catch((error: Error) => setError(error.message));
    }
  }, [gameId, name, navigate]);

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
        <Button onClick={onClick}>Join game {gameId}</Button>
        {error && <ErrorText error={error} />}
      </Stack>
    </Center>
  );
};

export default Invite;
