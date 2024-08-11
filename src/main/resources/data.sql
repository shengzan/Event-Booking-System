-- Insert sample users
INSERT INTO user (username, password, email, role, first_name, last_name, phone_number)
VALUES 
('admin', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu', 'admin@example.com', 'ADMIN', 'Admin', 'User', '1234567890'),
('organizer', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu', 'organizer@example.com', 'ORGANIZER', 'Event', 'Organizer', '2345678901'),
('user1', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu', 'user1@example.com', 'USER', 'John', 'Doe', '3456789012'),
('user2', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu', 'user2@example.com', 'USER', 'Jane', 'Smith', '4567890123')
ON DUPLICATE KEY UPDATE email=VALUES(email), role=VALUES(role), first_name=VALUES(first_name), last_name=VALUES(last_name), phone_number=VALUES(phone_number);

-- Insert sample events
INSERT INTO event (name, description, date, capacity, location, price, organizer_id)
VALUES 
('Summer Music Festival', 'A weekend of great music and fun', '2023-07-15', 1000, 'Central Park', 50.00, 2),
('Tech Conference 2023', 'Learn about the latest in technology', '2023-09-20', 500, 'Convention Center', 100.00, 2),
('Food and Wine Expo', 'Taste cuisines from around the world', '2023-08-05', 300, 'City Hall', 75.00, 2)
ON DUPLICATE KEY UPDATE description=VALUES(description), capacity=VALUES(capacity), price=VALUES(price), location=VALUES(location);

-- Insert sample orders
INSERT INTO `order` (user_id, order_date, status)
VALUES 
(3, '2023-06-01 10:00:00', 'PAID'),
(4, '2023-06-02 11:30:00', 'PAID'),
(3, '2023-06-03 09:15:00', 'CANCELLED')
ON DUPLICATE KEY UPDATE status=VALUES(status);

-- Insert sample tickets
INSERT INTO ticket (event_id, order_id, seat_number)
VALUES 
(1, 1, 101),
(1, 1, 102),
(2, 2, 201),
(3, 2, 301),
(1, 3, 103)
ON DUPLICATE KEY UPDATE seat_number=VALUES(seat_number);
