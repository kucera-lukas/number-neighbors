declare module "sockjs-client/dist/sockjs" {
  // eslint-disable-next-line unicorn/no-await-expression-member
  export default (await import("sockjs-client")).default;
}
