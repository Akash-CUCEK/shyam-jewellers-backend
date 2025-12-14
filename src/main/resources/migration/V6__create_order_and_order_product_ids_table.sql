CREATE TABLE `order` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(255),
    customer_email VARCHAR(255),
    customer_phone VARCHAR(50),
    address VARCHAR(500),
    order_date DATE,
    order_time TIME,
    order_status VARCHAR(50),
    delivery_type VARCHAR(100),
    total_cost DECIMAL(15, 2),
    due_amount DECIMAL(15, 2),
    payment_status VARCHAR(50),
    payment_method VARCHAR(50),
    notes TEXT,
    created_by_id BIGINT,
    created_by_role VARCHAR(50),
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE order_product_ids (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, product_id),
    CONSTRAINT fk_order_product_order FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE
);
