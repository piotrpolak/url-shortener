SELECT 'CREATE DATABASE urlshortener'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'urlshortener')\gexec