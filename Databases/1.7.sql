DROP TABLE IF EXISTS billing;

CREATE TABLE billing (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ref_no VARCHAR(20),
    bill_no VARCHAR(20),
    date DATE,
    table_no INT,
    item_name VARCHAR(100),
    category VARCHAR(50),
    quantity INT,
    rate DECIMAL(10,2),
    amount DECIMAL(10,2),
    vat DECIMAL(10,2),
    service_charge DECIMAL(10,2),
    discount DECIMAL(10,2),
    grand_total DECIMAL(10,2),
    paid_amount DECIMAL(10,2),
    return_amount DECIMAL(10,2),
    issued_by VARCHAR(50)
);
