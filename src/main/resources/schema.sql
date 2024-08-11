-- Drop tables if they exist
DROP TABLE IF EXISTS order;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS user;

-- Create User table
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone_number VARCHAR(20)
);

-- Create Event table
CREATE TABLE event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    date DATE NOT NULL,
    capacity INT NOT NULL,
    location VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    organizer_id BIGINT,
    FOREIGN KEY (organizer_id) REFERENCES user(id)
);

-- Create Order table
CREATE TABLE `order` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    order_date DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    CHECK (status IN ('PAID', 'REFUNDED', 'CANCELLED'))
);

-- Create Ticket table
CREATE TABLE ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id BIGINT,
    order_id BIGINT,
    seat_number INT,
    FOREIGN KEY (event_id) REFERENCES event(id),
    FOREIGN KEY (order_id) REFERENCES `order`(id)
);

-- Create index for faster queries
CREATE INDEX idx_event_date ON event(date);
CREATE INDEX idx_ticket_event ON ticket(event_id);
CREATE INDEX idx_order_user ON `order`(user_id);
