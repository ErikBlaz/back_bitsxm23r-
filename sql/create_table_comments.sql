CREATE TABLE Comments (
    id INTEGER auto_increment,
    userId INTEGER NOT NULL,
    threadId INTEGER NOT NULL,
    comment TEXT NOT NULL,
    likes INTEGER DEFAULT(0),
    created_at DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES Users(id),
    FOREIGN KEY (threadId) REFERENCES Threads(id)
);
