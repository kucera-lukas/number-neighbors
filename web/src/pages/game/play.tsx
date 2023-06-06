import OpponentNumbers from "../../components/play/opponent-numbers.component";
import UserNumbers from "../../components/play/user-numbers.component";
import { useGamePayload } from "../../context/game-payload.context";
import PageLayout from "../../layouts/page.layout";

const Play = (): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const enabled = gamePayload?.ready;

  return (
    <PageLayout title="play">
      {enabled && (
        <>
          <UserNumbers />
          <OpponentNumbers />
        </>
      )}
    </PageLayout>
  );
};

export default Play;
