import { AccordionValuesContext } from "../context/accordion.context";

import { Accordion } from "@mantine/core";
import { useEffect, useState } from "react";

import type { PropsWithChildren } from "react";

export type AccordionLayoutProps = PropsWithChildren<{
  defaultValues: string[];
  disabled?: boolean;
}>;

const AccordionLayout = ({
  defaultValues,
  disabled,
  children,
}: AccordionLayoutProps): JSX.Element => {
  const [values, setValues] = useState<string[]>(disabled ? [] : defaultValues);

  useEffect(() => {
    setValues(defaultValues);
  }, [defaultValues]);

  useEffect(() => {
    if (disabled) {
      setValues([]);
    }
  }, [disabled]);

  return (
    <AccordionValuesContext.Provider value={[values, setValues]}>
      <Accordion
        value={disabled ? [] : values}
        onChange={setValues}
        variant="separated"
        multiple
        {...(disabled ? {} : { defaultValue: defaultValues })}
      >
        {children}
      </Accordion>
    </AccordionValuesContext.Provider>
  );
};

export default AccordionLayout;
