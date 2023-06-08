import useTakeTurnForm from "./take-turn.hook";
import TakeTurnInput from "./take-turn.input";

import ErrorText from "../../components/errors/error.text";
import { SERVER_URI } from "../../config/environment";
import { useGamePayload } from "../../context/game-payload.context";
import useLocalStorageItem from "../../hooks/localstorage.hook";

import { Button, LoadingOverlay, Stack } from "@mantine/core";
import { useCallback, useState } from "react";

import type { TakeTurnFormValues } from "./take-turn.types";

const TakeTurnForm = (): JSX.Element => {
  const form = useTakeTurnForm();
  const [gamePayload] = useGamePayload();
  const [token] = useLocalStorageItem<string>("token");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>();

  const onSubmit = useCallback(
    (values: TakeTurnFormValues) => {
      if (gamePayload) {
        // eslint-disable-next-line unicorn/no-useless-undefined
        setError(undefined);
        setLoading(true);

        fetch(`${SERVER_URI}/turns?game=${gamePayload.id}`, {
          method: "POST",
          body: JSON.stringify({ value: +values.number }),
          headers: { Authorization: token, "Content-Type": "application/json" },
        })
          .then((res) => {
            if (!res.ok) {
              return res.text().then((text) => {
                throw new Error(text);
              });
            }
          })
          .catch((error: Error) => {
            setError(error.message);
          });

        setLoading(false);
      } else {
        setError("Invalid game");
      }
    },
    [gamePayload, token, setLoading, setError],
  );

  return (
    <form onSubmit={form.onSubmit(onSubmit)}>
      <LoadingOverlay visible={loading} />

      <Stack spacing="xs">
        <TakeTurnInput
          form={form}
          disabled={loading}
        />

        {error && <ErrorText error={error} />}

        <Button
          type="submit"
          disabled={loading}
          style={{ alignSelf: "flex-end" }}
        >
          Take turn
        </Button>
      </Stack>
    </form>
  );
};

export default TakeTurnForm;
