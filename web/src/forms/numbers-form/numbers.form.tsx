import NumbersFormInput from "./number-form.input";
import useNumbersForm from "./numbers-form.hook";

import ErrorText from "../../components/errors/error.text";
import { SERVER_URI } from "../../config/environment";
import { useGamePayload } from "../../context/game-payload.context";
import useLocalStorageItem from "../../hooks/localstorage.hook";

import { Button, LoadingOverlay, Stack } from "@mantine/core";
import { useCallback, useState } from "react";

import type { NumbersFormValues } from "./numbers-form.types";

const NumbersForm = (): JSX.Element => {
  const form = useNumbersForm();
  const [gamePayload] = useGamePayload();
  const [error, setError] = useState<string>();
  const [loading, setLoading] = useState(false);
  const [token] = useLocalStorageItem<string>("token");
  const user = gamePayload?.user;
  const disabled = user?.numbers.length === 3 || loading;

  const onSubmit = useCallback(
    (values: NumbersFormValues) => {
      if (!user) {
        return;
      }

      // eslint-disable-next-line unicorn/no-useless-undefined
      setError(undefined);
      setLoading(true);

      fetch(`${SERVER_URI}/numbers?player=${user.id}`, {
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
        })
        .catch((error: Error) => {
          setError(error.message);
        });

      setLoading(false);
    },
    [user, token],
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
