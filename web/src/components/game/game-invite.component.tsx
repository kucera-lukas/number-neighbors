import { useAccordionValues } from "../../context/accordion.context";
import { useGame } from "../../context/game.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { Button, CopyButton, Stack, Text } from "@mantine/core";
import { useEffect } from "react";
import { useHref } from "react-router-dom";

const GameInvite = (): JSX.Element => {
  const [, setAccordionValues] = useAccordionValues();
  const [game] = useGame();
  const disabled = game?.players.length === 2;
  const inviteHref = useHref("invite");
  const inviteLink = `${window.location.protocol}//${window.location.host}${inviteHref}`;

  useEffect(() => {
    if (disabled) {
      setAccordionValues((values) =>
        values.filter((value) => value !== "game-invite"),
      );
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [disabled]);

  return (
    <AccordionItemLayout
      title="Game Invite"
      value="game-invite"
      disabled={disabled}
    >
      <Stack spacing="xs">
        <Text size="sm">
          <>
            Invite link:
            <br />
            {
              <a
                onClick={(e) => e.preventDefault()}
                href={inviteHref}
              >
                {inviteLink}
              </a>
            }
          </>
        </Text>
        {
          <CopyButton value={inviteLink}>
            {({ copied, copy }) => (
              <Button
                color={copied ? "teal" : "blue"}
                onClick={copy}
                style={{ alignSelf: "flex-end" }}
              >
                Copy
              </Button>
            )}
          </CopyButton>
        }
      </Stack>
    </AccordionItemLayout>
  );
};

export default GameInvite;
