DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    user_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username   VARCHAR(50)          NOT NULL,
    email      VARCHAR(50)          NOT NULL,
    password   VARCHAR(255)         NOT NULL,
    is_verify BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ
);

DROP TABLE IF EXISTS user_accounts;
CREATE TABLE IF NOT EXISTS user_accounts (
    user_accounts_id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    provider VARCHAR(255) NOT NULL UNIQUE,
    provider_id VARCHAR(255) NOT NULL UNIQUE,
    access_token TEXT,
    refresh_token TEXT,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS verification_token;
CREATE TABLE IF NOT EXISTS verification_token(
    email VARCHAR(255) PRIMARY KEY,
    token TEXT,
    expire_at TIMESTAMP
);

DROP TABLE IF EXISTS otp_tokens;
CREATE TABLE IF NOT EXISTS otp_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    hash_otp TEXT NOT NULL,
    expire_date TIMESTAMP NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
)


