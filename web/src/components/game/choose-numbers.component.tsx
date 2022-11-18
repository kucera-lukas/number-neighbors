import { useAccordionValues } from "../../context/accordion.context";
import { useGamePayload } from "../../context/game-payload.context";
import NumbersForm from "../../forms/numbers-form/numbers.form";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { useEffect } from "react";

const ChooseNumbers = (): JSX.Element => {
  const [, setAccordionValues] = useAccordionValues();
  const [gamePayload] = useGamePayload();
  const disabled = gamePayload?.user?.numbers.length === 3;

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
