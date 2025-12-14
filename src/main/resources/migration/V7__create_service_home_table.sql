CREATE TABLE service_home (
    service_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    address VARCHAR(500),
    status VARCHAR(50),
    phone_number VARCHAR(50),
    service_type VARCHAR(50),
    notes TEXT,
    created_by VARCHAR(255),
    created_at DATETIME,
    updated_by VARCHAR(255),
    updated_at DATETIME
);
