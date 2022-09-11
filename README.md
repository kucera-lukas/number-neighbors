# number-neighbors

[![Continuous Integration](https://github.com/kucera-lukas/number-neighbors/actions/workflows/ci.yml/badge.svg)](https://github.com/kucera-lukas/number-neighbors/actions/workflows/ci.yml)
[![pre-commit.ci status](https://results.pre-commit.ci/badge/github/stegoer/server/main.svg)](https://results.pre-commit.ci/latest/github/stegoer/server/main)

number-neighbors is using Java and Postgres.

## Installation

### Install instructions

1. Install Java https://www.java.com/en/download/
2. Install Maven https://maven.apache.org/install.html
3. Install PostgreSQL https://www.postgresql.org/download/
4. Clone this repository

```sh
git clone git@github.com:kucera-lukas/number-neighbors.git
```

5. Install dependencies

```sh
mvn install
```

## Development

### Dev server

```sh
mvn spring-boot:run
```

### Maven

```sh
mvn --help
```

## Contributing

```sh
pre-commit install
```

## License

Developed under
the [MIT](https://github.com/kucera-lukas/number-neighbors/blob/master/LICENSE)
license.
