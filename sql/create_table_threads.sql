CREATE TABLE Threads (
    id INTEGER auto_increment,
    userId INTEGER NOT NULL,
    title VARCHAR(256) NOT NULL,
    theme VARCHAR(50) NOT NULL,
    description TEXT,
    created_at DATE NOT NULL,
    likes INTEGER DEFAULT(0),
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES Users(id)
);
selectByTheme = conn.prepareStatement("SELECT * FROM Threads WHERE theme = ?");