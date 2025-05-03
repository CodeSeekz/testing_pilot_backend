DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    user_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username   VARCHAR(50)          NOT NULL,
    email      VARCHAR(50)          NOT NULL,
    password   VARCHAR(255)         NOT NULL,
    isVerified BOOLEAN DEFAULT FALSE NOT NULL,
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

DROP TABLE IF EXISTS email_verifications;
CREATE TABLE IF NOT EXISTS email_verifications(
    email VARCHAR(255) PRIMARY KEY,
    token TEXT
)
