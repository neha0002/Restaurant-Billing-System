USE Kasturi_rbs;

DROP TABLE IF EXISTS staff;

CREATE TABLE IF NOT EXISTS staff (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    gender VARCHAR(10),
    contact VARCHAR(20),
    address VARCHAR(100),
    salary VARCHAR(20),
    username VARCHAR(50),
    password VARCHAR(50)
);


INSERT INTO staff (name, gender, contact, address, salary, username, password) VALUES
('Ramesh Sharma', 'Male', '9876543210', 'Kathmandu', '25000', 'ramesh', 'pass123'),
('Sita Rai', 'Female', '9811122233', 'Lalitpur', '27000', 'sita', 'sita@123'),
('Binod Thapa', 'Male', '9801122334', 'Bhaktapur', '26000', 'binod', 'thapa456'),
('Anjali Khadka', 'Female', '9845671234', 'Pokhara', '24000', 'anjali', 'anjali789'),
('Manish Basnet', 'Male', '9823456789', 'Butwal', '28000', 'manish', 'adminman'),
('Kabita Gurung', 'Female', '9808765432', 'Dharan', '25500', 'kabita', 'kabi321'),
('Rajesh Koirala', 'Male', '9812345678', 'Biratnagar', '26000', 'rajesh', 'rajko123'),
('Mina Shrestha', 'Female', '9841122334', 'Hetauda', '25000', 'mina', 'minash@22'),
('Suraj Adhikari', 'Male', '9865432109', 'Janakpur', '27000', 'suraj', 'suraj789'),
('Pratiksha Poudel', 'Female', '9809988776', 'Chitwan', '24500', 'pratiksha', 'pati123'),
('Kiran Lama', 'Male', '9811223344', 'Birgunj', '25500', 'kiran', 'kirlama456'),
('Sunita Bista', 'Female', '9845566778', 'Dhangadhi', '26000', 'sunita', 'sunib123'),
('Dipesh Maharjan', 'Male', '9866778899', 'Kathmandu', '27500', 'dipesh', 'dipma789'),
('Alisha Singh', 'Female', '9801122443', 'Lalitpur', '25000', 'alisha', 'ali@321'),
('Nabin Bhattarai', 'Male', '9877665544', 'Pokhara', '26500', 'nabin', 'nabpass1'),
('Ritika Chaudhary', 'Female', '9819988776', 'Nepalgunj', '25500', 'ritika', 'ritcha@98'),
('Arjun Gurung', 'Male', '9844433221', 'Dharan', '27000', 'arjun', 'arjg123'),
('Sneha Karki', 'Female', '9803344556', 'Bhaktapur', '25000', 'sneha', 'karsne456'),
('Bibek Shahi', 'Male', '9876547890', 'Itahari', '28000', 'bibek', 'bibsh987'),
('Pramila Subedi', 'Female', '9810001122', 'Bharatpur', '26000', 'pramila', 'prasub321'),
('Rohit Shrestha', 'Male', '9822233445', 'Banepa', '25000', 'rohit', 'roshre123'),
('Aayush Khatri', 'Male', '9807766554', 'Kathmandu', '26500', 'aayush', 'aayu123'),
('Puja Joshi', 'Female', '9813456789', 'Lalitpur', '25000', 'puja', 'puja@123'),
('Sandesh Rijal', 'Male', '9841122001', 'Biratnagar', '27500', 'sandesh', 'sand456'),
('Niruta Tamang', 'Female', '9802233445', 'Pokhara', '26000', 'niruta', 'niritam@55'),
('Anup Shrestha', 'Male', '9871234560', 'Dharan', '25500', 'anup', 'anu@pass'),
('Rachana Thapa', 'Female', '9815566778', 'Bhaktapur', '27000', 'rachana', 'racha123'),
('Keshav Adhikari', 'Male', '9803344557', 'Hetauda', '24500', 'keshav', 'kesh789'),
('Binita Kunwar', 'Female', '9845567788', 'Chitwan', '26000', 'binita', 'bini@321'),
('Prakash Giri', 'Male', '9811122235', 'Janakpur', '25000', 'prakash', 'pragiri45'),
('Muna Basnet', 'Female', '9806677889', 'Butwal', '27500', 'muna', 'munabas123');

