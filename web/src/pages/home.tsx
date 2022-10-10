import JoinGame from "../components/join-game.component";
import NewGame from "../components/new-game.component";
import PageLayout from "../layouts/page.layout";

import { Stack } from "@mantine/core";

const Home = (): JSX.Element => {
  return (
    <PageLayout title="home">
      <Stack>
        <NewGame />
        <JoinGame />
      </Stack>
    </PageLayout>
  );
};

export default Home;
