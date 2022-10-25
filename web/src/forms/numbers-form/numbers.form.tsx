import NumbersFormInput from "./number-form.input";
import useNumbersForm from "./numbers-form.hook";

import ErrorText from "../../components/errors/error.text";
import { SERVER_URI } from "../../config/environment";
import { usePlayer } from "../../context/player.context";
import useLocalStorageItem from "../../hooks/localstorage.hook";

import { Button, LoadingOverlay, Stack } from "@mantine/core";
import { useCallback, useState } from "react";

import type Player from "../../types/player.type";
import type { NumbersFormValues } from "./numbers-form.types";

const NumbersForm = (): JSX.Element => {
  const form = useNumbersForm();
  const [player, setPlayer] = usePlayer();
  const [error, setError] = useState<string>();
  const [loading, setLoading] = useState(false);
  const [token] = useLocalStorageItem<string>("token");
  const disabled = player?.numbers.length === 3 || loading;

  const resetError = useCallback(() => {
    // eslint-disable-next-line unicorn/no-useless-undefined
    setError(undefined);
  }, []);

  const onSubmit = useCallback(
    (values: NumbersFormValues) => {
      resetError();

      setLoading(true);

      fetch(`${SERVER_URI}/players/pick`, {
        method: "POST",
        body: JSON.stringify(values),
        headers: {
          Authorization: token,
          "Content-Type": "application/json",
        },
      })
        .then((res) => {
          if (!res.ok) {
            throw new Error(res.status.toLocaleString());
          }

          return res.json();
        })
        .then((res: Player) => {
          setPlayer(res);
          resetError();
        })
        .catch((error: Error) => {
          setError(error.message);
        });

      setLoading(false);
    },
    [resetError, setPlayer, token],
  );

  return (
    <form onSubmit={form.onSubmit(onSubmit)}>
      <LoadingOverlay visible={loading} />

      <Stack spacing="xs">
        <NumbersFormInput
          form={form}
          disabled={disabled}
        />

        {error && <ErrorText error={error} />}

        <Button
          type="submit"
          disabled={disabled}
          style={{ alignSelf: "flex-end" }}
        >
          Set numbers
        </Button>
      </Stack>
    </form>
  );
};

export default NumbersForm;
