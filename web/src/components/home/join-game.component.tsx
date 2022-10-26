import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { NumberInput, Button, Stack } from "@mantine/core";
import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";

const JoinGame = (): JSX.Element => {
  const navigate = useNavigate();
  const [gameID, setGameID] = useState<number>();
  const [error, setError] = useState<string>();

  const onJoin = useCallback(() => {
    // eslint-disable-next-line unicorn/no-useless-undefined
    setError(undefined);

    if (gameID === undefined) {
      setError("Game identifier is required");
    } else if (gameID < 0) {
      setError("Game identifier is not valid");
    } else {
      navigate(`/game/${gameID}`);
    }
  }, [gameID, navigate]);

  return (
    <AccordionItemLayout
      title="Join Game"
      value="join-game"
    >
      <Stack spacing="xs">
        <NumberInput
          placeholder="Game to join"
          label="Game identifier"
          withAsterisk
          value={gameID}
          onChange={(value) => setGameID(value)}
          min={0}
          hideControls
          error={error}
        />
        <Button
          onClick={onJoin}
          disabled={gameID === undefined}
          style={{ alignSelf: "flex-end" }}
        >
          Join
        </Button>
      </Stack>
    </AccordionItemLayout>
  );
};

export default JoinGame;
