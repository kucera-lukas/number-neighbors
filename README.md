# number-neighbors

[![Continuous Integration](https://github.com/kucera-lukas/number-neighbors/actions/workflows/ci.yml/badge.svg)](https://github.com/kucera-lukas/number-neighbors/actions/workflows/ci.yml)
[![pre-commit.ci status](https://results.pre-commit.ci/badge/github/stegoer/server/main.svg)](https://results.pre-commit.ci/latest/github/stegoer/server/main)

number-neighbors is using Java and Postgres.

## Installation

```sh
git clone git@github.com:kucera-lukas/number-neighbors.git

# generate RSA key pair
bash scripts/gen-keys.bash
```

### Local

1. Install Java https://www.java.com/en/download/
2. Install Maven https://maven.apache.org/install.html
3. Install PostgreSQL https://www.postgresql.org/download/
4. Fill environment variables into .env file
5. Install dependencies

```sh
mvn clean package -DskipTests=true
```

### Docker

Installation using Docker is recommended if you don't want to install all
dependencies locally on your machine.

## Development

### Server

```sh
# local
mvn spring-boot:run -Dspring.profiles.active=dev

# docker
docker-compose up -d
```

## Contributing

```sh
pre-commit install
```

## License

Developed under
the [MIT](https://github.com/kucera-lukas/number-neighbors/blob/master/LICENSE)
license.
