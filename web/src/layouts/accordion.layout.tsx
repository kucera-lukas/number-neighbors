import { Accordion, Text } from "@mantine/core";

import type { PropsWithChildren } from "react";

export type AccordionLayoutProps = PropsWithChildren<{
  value: string;
  title: string;
  description?: string;
  disabled?: boolean;
}>;

const AccordionLayout = ({
  value,
  title,
  description,
  disabled,
  children,
}: AccordionLayoutProps): JSX.Element => {
  return (
    <Accordion
      variant="separated"
      {...(disabled ? {} : { defaultValue: value })}
    >
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
    </Accordion>
  );
};

export default AccordionLayout;
