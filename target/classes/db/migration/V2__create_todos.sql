CREATE TABLE todos (
    id SERIAL PRIMARY KEY,
    todo VARCHAR(255) NOT NULL,
    completed BOOLEAN NOT NULL,
    userId INT,
    FOREIGN KEY (userId) REFERENCES users(id)
);