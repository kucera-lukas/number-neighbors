import Sock from "../components/sock";

import { Center, Stack, Title } from "@mantine/core";

const Home = (): JSX.Element => {
  return (
    <Center>
      <Stack>
        <Title>Number Neighbors</Title>
        <Sock />
      </Stack>
    </Center>
  );
};

export default Home;
