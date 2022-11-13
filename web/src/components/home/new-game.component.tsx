import { SERVER_URI } from "../../config/environment";
import useLocalStorageItem from "../../hooks/localstorage.hook";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { TextInput, Button, Stack } from "@mantine/core";
import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";

import type GamePayload from "../../types/game-payload.type";

type NewGameResponse = {
  game: GamePayload;
  token: string;
};

const NewGame = (): JSX.Element => {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [, setToken] = useLocalStorageItem<string>("token");
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
          setToken(`Bearer ${res.token}`);

          navigate(`/game/${res.game.id}`);
        })
        .catch((error: Error) => {
          setError(error.message);
          setLoading(false);
        });
    }
  }, [name, navigate, setToken]);

  return (
    <AccordionItemLayout
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
    </AccordionItemLayout>
  );
};

export default NewGame;
