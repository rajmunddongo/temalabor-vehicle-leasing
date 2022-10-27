# Dev environment

### Setup database

`docker run --rm --name vehicle-leasing-dev-db -p 5432:5432/tcp -e POSTGRES_PASSWORD=password -d postgres`