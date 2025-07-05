DROP TABLE IF EXISTS reservation;

CREATE TABLE reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(20),
    customer_name VARCHAR(100),
    customer_address VARCHAR(100),
    contact_no VARCHAR(20),
    res_date DATE,
    res_time VARCHAR(10),
    table_no INT
);

SELECT * FROM reservation WHERE res_date = '2025-05-29';



