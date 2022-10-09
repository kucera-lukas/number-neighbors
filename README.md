# number-neighbors

[![Netlify Status](https://api.netlify.com/api/v1/badges/5756862d-7c71-4bcb-98f8-cd0a563eda92/deploy-status)](https://app.netlify.com/sites/number-neighbors/deploys)
[![Continuous Integration](https://github.com/kucera-lukas/number-neighbors/actions/workflows/ci.yml/badge.svg)](https://github.com/kucera-lukas/number-neighbors/actions/workflows/ci.yml)
[![pre-commit.ci status](https://results.pre-commit.ci/badge/github/stegoer/server/main.svg)](https://results.pre-commit.ci/latest/github/stegoer/server/main)

number-neighbors is using Java and Postgres.

## Installation

1. Clone the repository

```sh
git clone git@github.com:kucera-lukas/number-neighbors.git
```

2. Generate RSA key pair

```sh
bash scripts/gen-keys.bash
```

### Server

#### Local

1. Install Java https://www.java.com/en/download/
2. Install Maven https://maven.apache.org/install.html
3. Install PostgreSQL https://www.postgresql.org/download/
4. Install dependencies

```sh
mvn clean package -DskipTests=true
```

5. Fill environment variables into .env file

```sh
# in the src/main/resources directory
cp .env.example .env
```

#### Docker

Installation using Docker is recommended if you don't want to install all
dependencies locally on your machine.

Currently no additional setup is required.

### Client

```shell
# in the web directory
npm install
```

## Development

### Server

#### Local

```sh
mvn spring-boot:run -Dspring.profiles.active=dev
```

#### Docker

```sh
docker-compose up
```

### Client

```shell
# in the web directory
npm run dev
```

## Contributing

```sh
pre-commit install
```

## License

Developed under
the [MIT](https://github.com/kucera-lukas/number-neighbors/blob/master/LICENSE)
license.
