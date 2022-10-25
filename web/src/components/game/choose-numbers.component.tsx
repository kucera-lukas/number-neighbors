import { usePlayer } from "../../context/player.context";
import NumbersForm from "../../forms/numbers-form/numbers.form";
import AccordionLayout from "../../layouts/accordion.layout";

const ChooseNumbers = (): JSX.Element => {
  const [player] = usePlayer();
  const disabled = player?.numbers.length == 3;

  return (
    <AccordionLayout
      title="Choose Numbers"
      value="choose-numbers"
      disabled={disabled}
    >
      <NumbersForm />
    </AccordionLayout>
  );
};

export default ChooseNumbers;
