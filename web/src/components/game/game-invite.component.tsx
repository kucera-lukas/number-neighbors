import AccordionLayout from "../../layouts/accordion.layout";

import { Button, CopyButton, Stack, Text } from "@mantine/core";
import { useHref } from "react-router-dom";

const GameInvite = (): JSX.Element => {
  const inviteHref = useHref("invite");
  const inviteLink = `${window.location.protocol}//${window.location.host}${inviteHref}`;

  return (
    <AccordionLayout
      title="Game Invite"
      value="game-invite"
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
    </AccordionLayout>
  );
};

export default GameInvite;
