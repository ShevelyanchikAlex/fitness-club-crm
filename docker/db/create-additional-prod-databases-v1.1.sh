#!/bin/bash
set -e

echo "Create additional DBs"

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL

    -- order_service DB
    CREATE DATABASE "order_service_db";
    GRANT ALL PRIVILEGES ON DATABASE "order_service_db" TO $POSTGRES_USER;

EOSQL