import JoinGame from "../components/home/join-game.component";
import NewGame from "../components/home/new-game.component";
import AccordionLayout from "../layouts/accordion.layout";
import PageLayout from "../layouts/page.layout";

const Home = (): JSX.Element => {
  return (
    <PageLayout title="home">
      <AccordionLayout defaultValues={["new-game", "join-game"]}>
        <NewGame />
        <JoinGame />
      </AccordionLayout>
    </PageLayout>
  );
};

export default Home;
