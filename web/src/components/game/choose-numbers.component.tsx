import { useAccordionValues } from "../../context/accordion.context";
import { usePlayer } from "../../context/player.context";
import NumbersForm from "../../forms/numbers-form/numbers.form";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { useEffect } from "react";

const ChooseNumbers = (): JSX.Element => {
  const [, setAccordionValues] = useAccordionValues();
  const [player] = usePlayer();
  const disabled = player?.numbers.length == 3;

  useEffect(() => {
    if (disabled) {
      setAccordionValues((values) =>
        values.filter((value) => value !== "choose-numbers"),
      );
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [disabled]);

  return (
    <AccordionItemLayout
      title="Choose Numbers"
      value="choose-numbers"
      disabled={disabled}
    >
      <NumbersForm />
    </AccordionItemLayout>
  );
};

export default ChooseNumbers;
