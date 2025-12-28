CREATE TABLE category (
                          category_id BIGINT PRIMARY KEY IDENTITY(1,1),
                          name VARCHAR(255) NOT NULL,
                          show_on_home BIT NOT NULL DEFAULT 0,
                          image_url VARCHAR(500),
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          created_by VARCHAR(255),
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_by VARCHAR(255),
                          status BIT NOT NULL
);
