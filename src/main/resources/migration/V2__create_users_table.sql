CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255),
    otp VARCHAR(255),
    otp_generated_time DATETIME
);
