﻿# Products

## Connect to Postgres Database

```
docker run --name shop-db -p 5431:5432 -e POSTGRES_USERNAME=postgres -e POSTGRES_PASSWORD=postgres postgres:14.1
```

### Create database into container (using CLI)

```
docker exec -it shop-db /bin/bash

psql -U postgres -d postgres

create database shop;

\c shop

create schema shop;
```
