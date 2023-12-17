CREATE TABLE Users(
    id INTEGER auto_increment,
    email VARCHAR(50) NOT NULL,
    username VARCHAR(20) NOT NULL,
    password TEXT NOT NULL,
    token TEXT NOT NULL,
    hash TEXT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username),
    UNIQUE (email)
);