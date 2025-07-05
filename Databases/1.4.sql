CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cat_name VARCHAR(100) UNIQUE
);

-- Sample categories
INSERT INTO category (cat_name) VALUES 
('Momo'), ('Chowmein'), ('Pizza'), ('Snacks'), ('Drinks'), ('Desserts');
