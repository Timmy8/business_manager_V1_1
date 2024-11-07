CREATE TABLE IF NOT EXISTS tg_bot_user
(
    id UUID PRIMARY KEY,
    chat_id BIGINT,
    user_tag VARCHAR(30) CHECK(LENGTH(TRIM(user_tag)) > 0)
);

CREATE TABLE IF NOT EXISTS email_user
(
    id UUID PRIMARY KEY,
    name VARCHAR(30) CHECK(LENGTH(TRIM(name)) > 0),
    email VARCHAR(255) CHECK(LENGTH(email) > 0)
);