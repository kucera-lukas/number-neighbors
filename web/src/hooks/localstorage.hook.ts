import { STORAGE_KEY_PREFIX } from "../constants/storage.constants";

import { useLocalStorage } from "@mantine/hooks";

const useLocalStorageItem = <T>(
  key: string,
  defaultValue?: T,
): readonly [T, (val: T | ((prevState: T) => T)) => void, () => void] => {
  return useLocalStorage({
    key: `${STORAGE_KEY_PREFIX}${key}`,
    defaultValue: defaultValue,
  });
};

export default useLocalStorageItem;
