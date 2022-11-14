import { SERVER_URI } from "../../config/environment";
import PageLayout from "../../layouts/page.layout";
import LocalStorageService from "../../services/local-storage.service";

import { Button, Stack, TextInput } from "@mantine/core";
import { useCallback, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import type UserPayload from "../../types/user-payload.type";

type NewPlayerResponse = {
  player: UserPayload;
  token: string;
};

const Invite = (): JSX.Element => {
  const navigate = useNavigate();
  const params = useParams();
  const gameId = params.gameId as string;
  const [name, setName] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>();

  const onJoin = useCallback(() => {
    if (!name) {
      setError("Name is required");
    } else {
      setLoading(true);
      // eslint-disable-next-line unicorn/no-useless-undefined
      setError(undefined);

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
          LocalStorageService.set("token", `Bearer ${res.token}`);

          navigate(`/game/${gameId}`);
        })
        .catch((error: Error) => {
          setError(error.message);
          setLoading(false);
        });
    }
  }, [gameId, name, navigate]);

  return (
    <PageLayout title="invite">
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
          onClick={onJoin}
          disabled={loading || !name}
          style={{ alignSelf: "flex-end" }}
        >
          Join game {gameId}
        </Button>
      </Stack>
    </PageLayout>
  );
};

export default Invite;
