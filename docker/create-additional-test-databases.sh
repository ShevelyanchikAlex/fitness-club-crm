#!/bin/bash
set -e

echo "Create additional test DBs"

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL

    -- test_order_service DB
    CREATE DATABASE "test_order_service_db";
    GRANT ALL PRIVILEGES ON DATABASE "test_order_service_db" TO $POSTGRES_USER;

EOSQL