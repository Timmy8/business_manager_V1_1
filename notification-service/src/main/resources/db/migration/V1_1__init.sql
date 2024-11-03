CREATE SCHEMA IF NOT EXISTS manager;

CREATE TABLE IF NOT EXISTS manager.tg_bot_user
(
    id UUID PRIMARY KEY,
    chat_id BIGINT,
    user_tag VARCHAR(30) CHECK(LENGTH(TRIM(user_tag)) > 0)
);

CREATE TABLE IF NOT EXISTS manager.email_user
(
    id UUID PRIMARY KEY,
    name VARCHAR(30) CHECK(LENGTH(TRIM(name)) > 0),
    email VARCHAR(255) CHECK(LENGTH(email) > 0)
);

INSERT INTO manager.email_user (id, name, email) VALUES('046d43d5-ce74-4a2d-95ed-d2ae0f313d21', 'test', 'example@gmail.com');