import { useTurns } from "../../context/turns.context";
import AccordionItemLayout from "../../layouts/accordion-item.layout";

import { List } from "@mantine/core";

const Turns = (): JSX.Element => {
  const [turns] = useTurns();

  return (
    <AccordionItemLayout
      title="Turns"
      value="turns"
    >
      <List>
        {turns?.map((turn) => (
          <List.Item key={turn.id.toString()}>{JSON.stringify(turn)}</List.Item>
        ))}
      </List>
    </AccordionItemLayout>
  );
};

export default Turns;
