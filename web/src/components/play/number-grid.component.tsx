import { SimpleGrid } from "@mantine/core";

import type { PropsWithChildren } from "react";

export type NumberGridProps = PropsWithChildren<{
  cols: number;
}>;

const NumberGrid = ({ cols, children }: NumberGridProps): JSX.Element => {
  return (
    <SimpleGrid
      cols={cols}
      spacing="xs"
      verticalSpacing="xs"
    >
      {children}
    </SimpleGrid>
  );
};

export default NumberGrid;
