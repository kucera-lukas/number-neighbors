import PlayAccordion from "../../components/play/play.accordion";
import { useGamePayload } from "../../context/game-payload.context";
import PageLayout from "../../layouts/page.layout";

const Play = (): JSX.Element => {
  const [gamePayload] = useGamePayload();
  const enabled = gamePayload?.ready;

  return <PageLayout title="play">{enabled && <PlayAccordion />}</PageLayout>;
};

export default Play;
