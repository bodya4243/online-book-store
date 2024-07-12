-- set-up.sql
CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT,
    cover_image VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE
);

INSERT INTO books (title, isbn, price, author, description, cover_image, is_deleted) VALUES
('Sample Book 1', '9781111111111', 19.99, 'Author A', 'Sample book description 1', 'http://example.com/cover1.jpg', false),
('Sample Book 2', '9782222222222', 24.99, 'Author B', 'Sample book description 2', 'http://example.com/cover2.jpg', false),
('Sample Book 3', '9783333333333', 29.99, 'Author C', 'Sample book description 3', 'http://example.com/cover3.jpg', false);
