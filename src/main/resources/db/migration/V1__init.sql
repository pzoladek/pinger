CREATE TABLE logs (
    id SERIAL PRIMARY KEY,
    message VARCHAR(255),
    local_time TIMESTAMP,
    request_time BIGINT
);

INSERT INTO logs (message, local_time, request_time) VALUES (
    'Hello pinger - example',
    '2033-01-08 04:05:06',
    1234567890
);