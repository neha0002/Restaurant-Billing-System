CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    gender ENUM('Male', 'Female'),
    contact VARCHAR(20),
    address VARCHAR(100),
    salary VARCHAR(20),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50)
);

-- Sample employee
INSERT INTO employee (name, gender, contact, address, salary, username, password)
VALUES ('Rajiv', 'Male', '979047504', 'Nardevi', '32423', 'rajiv', 'rajiv');
