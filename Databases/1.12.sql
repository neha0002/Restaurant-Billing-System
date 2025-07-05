DROP TABLE IF EXISTS admin_users;

CREATE TABLE admin_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

INSERT INTO admin_users (username, password) VALUES 
('admin', 'admin123'),
('manager', 'manager123');


