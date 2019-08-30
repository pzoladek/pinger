CREATE TABLE subscribers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    url VARCHAR(255),
    subscription_date TIMESTAMP
);