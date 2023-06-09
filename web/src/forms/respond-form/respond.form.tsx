import useRespondForm from "./respond.hook";
import RespondInput from "./respond.input";

import ErrorText from "../../components/errors/error.text";
import { SERVER_URI } from "../../config/environment";
import useLocalStorageItem from "../../hooks/localstorage.hook";

import { Button, LoadingOverlay, Stack } from "@mantine/core";
import { useCallback, useState } from "react";

import type TurnPayload from "../../types/turn-payload.type";
import type { RespondFormValues } from "./respond.types";

export type RespondFormProps = {
  currentTurn: TurnPayload;
};

const RespondForm = ({ currentTurn }: RespondFormProps): JSX.Element => {
  const form = useRespondForm();
  const [token] = useLocalStorageItem<string>("token");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>();

  const onSubmit = useCallback(
    (values: RespondFormValues) => {
      // eslint-disable-next-line unicorn/no-useless-undefined
      setError(undefined);
      setLoading(true);

      fetch(`${SERVER_URI}/responses?turn=${currentTurn.id}`, {
        method: "POST",
        body: JSON.stringify({ type: values.type }),
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
    },
    [currentTurn.id, token, setLoading, setError],
  );

  return (
    <form onSubmit={form.onSubmit(onSubmit)}>
      <LoadingOverlay visible={loading} />

      <Stack spacing="xs">
        <RespondInput
          form={form}
          disabled={loading}
        />

        {error && <ErrorText error={error} />}

        <Button
          type="submit"
          size="xs"
          disabled={loading}
          style={{ alignSelf: "flex-end" }}
        >
          Respond
        </Button>
      </Stack>
    </form>
  );
};

export default RespondForm;
