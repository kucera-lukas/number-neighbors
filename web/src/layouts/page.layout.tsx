import { Center, Stack, Title, UnstyledButton } from "@mantine/core";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

import type { PropsWithChildren } from "react";

const BASE_TITLE = "number-neighbors";

export type PageLayoutProps = PropsWithChildren<{
  title: string;
}>;

const PageLayout = ({ children, title }: PageLayoutProps): JSX.Element => {
  const navigate = useNavigate();

  useEffect(() => {
    document.title = `${BASE_TITLE} | ${title}`;
  }, [title]);

  return (
    <Center>
      <Stack>
        <UnstyledButton onClick={() => navigate("/")}>
          <Title>number-neighbors</Title>
        </UnstyledButton>
        {children}
      </Stack>
    </Center>
  );
};

export default PageLayout;
