
-- Insert sample customers
INSERT INTO customer (name, email, phoneNumber) VALUES ('John Doe', 'john@example.com', '123456789');

-- Insert subscribed categories for the customer
INSERT INTO customer_subscribed_categories (customer_id, category) VALUES
((SELECT id FROM customer WHERE name = 'John Doe'), 'SPORTS'),
((SELECT id FROM customer WHERE name = 'John Doe'), 'FINANCE');

-- Insert notification channels for the customer
INSERT INTO customer_notification_channels (customer_id, channel) VALUES
((SELECT id FROM customer WHERE name = 'John Doe'), 'SMS'),
((SELECT id FROM customer WHERE name = 'John Doe'), 'EMAIL');

