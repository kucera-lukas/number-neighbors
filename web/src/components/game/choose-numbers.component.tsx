import { usePlayer } from "../../context/player.context";
import NumbersForm from "../../forms/numbers-form/numbers.form";
import AccordionLayout from "../../layouts/accordion.layout";

import { Stack } from "@mantine/core";

const ChooseNumbers = (): JSX.Element => {
  const [player] = usePlayer();
  const disabled = player?.numbers.length == 3;

  return (
    <AccordionLayout
      title="Choose Numbers"
      value="choose-numbers"
      disabled={disabled}
    >
      <Stack spacing="xs">
        <NumbersForm />
      </Stack>
    </AccordionLayout>
  );
};

export default ChooseNumbers;
