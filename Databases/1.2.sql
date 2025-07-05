CREATE TABLE login (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    usertype ENUM('Admin', 'User') NOT NULL
);

-- Sample entries
INSERT INTO login (username, password, usertype) VALUES 
('admin', 'admin123', 'Admin'),
('user1', 'user123', 'User');
