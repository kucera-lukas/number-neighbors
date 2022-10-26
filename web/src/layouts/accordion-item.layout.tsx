import { Accordion, Text } from "@mantine/core";

import type { PropsWithChildren } from "react";

export type AccordionItemLayoutProps = PropsWithChildren<{
  value: string;
  title: string;
  description?: string;
  disabled?: boolean;
}>;

const AccordionItemLayout = ({
  value,
  title,
  description,
  disabled,
  children,
}: AccordionItemLayoutProps): JSX.Element => {
  return (
    <Accordion.Item value={value}>
      <Accordion.Control disabled={disabled}>
        <Text size="md">{title}</Text>
        {description && (
          <Text
            size="xs"
            color="dimmed"
          >
            {description}
          </Text>
        )}
      </Accordion.Control>
      <Accordion.Panel>{children}</Accordion.Panel>
    </Accordion.Item>
  );
};

export default AccordionItemLayout;
