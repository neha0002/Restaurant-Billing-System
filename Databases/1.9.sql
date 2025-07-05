CREATE TABLE bill_print (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bill_id INT,
    print_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    printed_by VARCHAR(50)
);
