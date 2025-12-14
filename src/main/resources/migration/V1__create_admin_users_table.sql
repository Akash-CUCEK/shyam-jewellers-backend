CREATE TABLE admin_users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    otp VARCHAR(10),
    otp_generated_time DATETIME,
    name VARCHAR(255),
    phone_number VARCHAR(20),
    image_url VARCHAR(500),
    role VARCHAR(50),
    UNIQUE KEY unique_email (email)
);
