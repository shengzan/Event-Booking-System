-- Insert sample users
INSERT INTO user (username, password, email, role, first_name, last_name, phone_number)
VALUES 
('jason', '123456', 'admin@example.com', 'ADMIN', 'Admin', 'User', '1234567890'),
('organizer', '123456', 'organizer@example.com', 'ORGANIZER', 'Event', 'Organizer', '2345678901')
;

-- Insert sample events
INSERT INTO event (name, description, date, capacity, available_capacity, location, price, organizer_id)
VALUES 
('Summer Music Festival', 'A weekend of great music and fun', '2023-07-15', 1000, 1000, 'Central Park', 50.00, 2),
('Tech Conference 2023', 'Learn about the latest in technology', '2023-09-20', 500, 500, 'Convention Center', 100.00, 2),
('Food and Wine Expo', 'Taste cuisines from around the world', '2023-08-05', 300, 300, 'City Hall', 75.00, 2);


-- Insert sample orders with correct user_id values
INSERT INTO `order` (user_id, order_date, status)
VALUES 
(1, '2023-06-01 10:00:00', 'PAID'),
(2, '2023-06-02 11:30:00', 'PAID'),
(1, '2023-06-03 09:15:00', 'CANCELLED')
;

-- Insert sample tickets with correct event_id values
INSERT INTO ticket (event_id, order_id, seat_number)
VALUES 
(1, 1, 101),
(1, 1, 102),
(2, 2, 201),
(3, 2, 301),
(1, 3, 103)
;
