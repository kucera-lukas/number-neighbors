export const handleError = (res: Response): Promise<never> =>
  res.text().then((text) => {
    throw new Error(`${res.status.toLocaleString()}: ${text}`);
  });
