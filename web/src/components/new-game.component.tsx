import { SERVER_URI } from "../config/environment";
import AccordionLayout from "../layouts/accordion.layout";
import LocalStorageService from "../services/local-storage.service";

import { TextInput, Button, Stack } from "@mantine/core";
import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";

import type Game from "../types/game.type";

type NewGameResponse = {
  game: Game;
  token: string;
};

const NewGame = (): JSX.Element => {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>();

  const onCreate = useCallback(() => {
    if (!name) {
      setError("Player name is required");
    } else {
      setLoading(true);
      // eslint-disable-next-line unicorn/no-useless-undefined
      setError(undefined);

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
          LocalStorageService.set("token", `Bearer ${res.token}`);
          navigate(`/game/${res.game.id}`);
        })
        .catch((error: Error) => {
          setError(error.message);
          setLoading(false);
        });
    }
  }, [name, navigate]);

  return (
    <AccordionLayout
      title="New Game"
      value="new-game"
    >
      <Stack spacing="xs">
        <TextInput
          placeholder="Your name"
          label="Player name"
          withAsterisk
          value={name}
          onChange={(event) => setName(event.currentTarget.value)}
          disabled={loading}
          error={error}
        />
        <Button
          onClick={onCreate}
          disabled={loading || !name}
          style={{ alignSelf: "flex-end" }}
        >
          Create
        </Button>
      </Stack>
    </AccordionLayout>
  );
};

export default NewGame;
