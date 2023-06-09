import useAnswerForm from "./answer.hook";
import AnswerInput from "./answer.input";

import ErrorText from "../../components/errors/error.text";
import { SERVER_URI } from "../../config/environment";
import useLocalStorageItem from "../../hooks/localstorage.hook";
import { handleError } from "../../utils/error.utils";

import { Button, LoadingOverlay, Stack } from "@mantine/core";
import { useCallback, useState } from "react";

import type ResponsePayload from "../../types/response-payload.type";
import type { AnswerFormValues } from "./answer.types";

export type AnswerFormProps = {
  currentResponse: ResponsePayload;
};

const AnswerForm = ({ currentResponse }: AnswerFormProps): JSX.Element => {
  const form = useAnswerForm();
  const [token] = useLocalStorageItem<string>("token");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string>();

  const onSubmit = useCallback(
    (values: AnswerFormValues) => {
      // eslint-disable-next-line unicorn/no-useless-undefined
      setError(undefined);
      setLoading(true);

      fetch(`${SERVER_URI}/answers?response=${currentResponse.id}`, {
        method: "POST",
        body: JSON.stringify({ type: values.type }),
        headers: { Authorization: token, "Content-Type": "application/json" },
      })
        .then((res) => {
          if (!res.ok) {
            return handleError(res);
          }
        })
        .catch((error: Error) => {
          setError(error.message);
        })
        .finally(() => setLoading(false));
    },
    [currentResponse.id, token, setLoading, setError],
  );

  return (
    <form onSubmit={form.onSubmit(onSubmit)}>
      <LoadingOverlay visible={loading} />

      <Stack spacing="xs">
        <AnswerInput
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

export default AnswerForm;
