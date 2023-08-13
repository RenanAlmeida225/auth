CREATE TABLE users (
    id VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_enabled BOOLEAN NOT NULL,
    role VARCHAR(255) CHECK (role IN ('ADMIN', 'USER')),
    PRIMARY KEY (id)
);
CREATE TABLE confirmations (
    id SERIAL,
    token VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) UNIQUE NOT NULL,
    create_at TIMESTAMP(6),
    PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS confirmations
ADD CONSTRAINT FK_confirmations_users FOREIGN KEY (user_id) REFERENCES users;
INSERT INTO users (id, email, password, is_enabled, role)
VALUES (
        'a752b789-5398-4b48-8900-c47040f7d573',
        'admin@mail.com',
        '$2a$10$qqiRqlriql2PylHxxnwruey4L5KAH9bIyMVUWL5utseymYaV5z3Ue',
        TRUE,
        'ADMIN'
    );