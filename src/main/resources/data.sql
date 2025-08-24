 -- Dummy data for Property Plug application
-- This script will populate the database with sample data for frontend testing

-- Insert sample users (password is 'password123' encoded with BCrypt)
INSERT INTO users (id, full_name, email, phone_number, password, user_type, is_enabled, verification_token, reset_token, reset_token_expiry, created_at, updated_at) VALUES
('550e8400-e29b-41d4-a716-446655440001', 'John Smith', 'john.smith@example.com', '+1234567890', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'PROPERTY_OWNER', true, NULL, NULL, NULL, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440002', 'Sarah Johnson', 'sarah.johnson@example.com', '+1234567891', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'PROPERTY_OWNER', true, NULL, NULL, NULL, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440003', 'Mike Wilson', 'mike.wilson@example.com', '+1234567892', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'PROPERTY_OWNER', true, NULL, NULL, NULL, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440004', 'Emily Davis', 'emily.davis@example.com', '+1234567893', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'PROPERTY_TENANT', true, NULL, NULL, NULL, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440005', 'David Brown', 'david.brown@example.com', '+1234567894', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'PROPERTY_TENANT', true, NULL, NULL, NULL, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440006', 'Lisa Anderson', 'lisa.anderson@example.com', '+1234567895', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'PROPERTY_OWNER', true, NULL, NULL, NULL, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440007', 'Robert Taylor', 'robert.taylor@example.com', '+1234567896', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'PROPERTY_OWNER', true, NULL, NULL, NULL, NOW(), NOW()),
('550e8400-e29b-41d4-a716-446655440008', 'Jennifer White', 'jennifer.white@example.com', '+1234567897', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'PROPERTY_TENANT', true, NULL, NULL, NULL, NOW(), NOW());

-- Insert sample properties
INSERT INTO property (id, title, description, location, price, type, rating, reviews_count, owner_id, created_at, updated_at) VALUES
('660e8400-e29b-41d4-a716-446655440001', 'Modern Downtown Apartment', 'Beautiful 2-bedroom apartment in the heart of downtown with city views, modern amenities, and walking distance to restaurants and shops.', 'Downtown City Center', 2500.00, 'Rent', 4.5, 12, '550e8400-e29b-41d4-a716-446655440001', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440002', 'Luxury Beachfront Villa', 'Stunning 4-bedroom villa with direct beach access, private pool, and panoramic ocean views. Perfect for families or vacation rentals.', 'Beachfront Paradise', 850000.00, 'Sale', 4.8, 8, '550e8400-e29b-41d4-a716-446655440001', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440003', 'Cozy Suburban House', 'Charming 3-bedroom house in quiet suburban neighborhood with large backyard, garage, and excellent schools nearby.', 'Suburban Heights', 450000.00, 'Sale', 4.2, 15, '550e8400-e29b-41d4-a716-446655440002', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440004', 'Urban Loft Space', 'Industrial-style loft with high ceilings, exposed brick, and modern appliances. Perfect for young professionals.', 'Arts District', 1800.00, 'Rent', 4.3, 9, '550e8400-e29b-41d4-a716-446655440002', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440005', 'Mountain Cabin Retreat', 'Rustic 2-bedroom cabin surrounded by nature with hiking trails, fireplace, and stunning mountain views.', 'Mountain Valley', 320000.00, 'Sale', 4.6, 11, '550e8400-e29b-41d4-a716-446655440003', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440006', 'Family Townhouse', 'Spacious 4-bedroom townhouse with community amenities including pool, gym, and playground. Great for families.', 'Family Community', 2800.00, 'Rent', 4.4, 18, '550e8400-e29b-41d4-a716-446655440003', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440007', 'Historic City Home', 'Beautifully restored 3-bedroom historic home with original features, modern updates, and prime city location.', 'Historic District', 650000.00, 'Sale', 4.7, 14, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440008', 'Waterfront Condo', 'Luxurious 2-bedroom condo with marina views, balcony, and access to boat dock. Perfect for water enthusiasts.', 'Marina Bay', 2100.00, 'Rent', 4.5, 13, '550e8400-e29b-41d4-a716-446655440006', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440009', 'Garden Studio Apartment', 'Charming studio apartment with private garden, updated kitchen, and walking distance to public transportation.', 'Garden District', 1200.00, 'Rent', 4.1, 7, '550e8400-e29b-41d4-a716-446655440007', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440010', 'Modern Office Space', 'Professional office space with meeting rooms, reception area, and modern technology infrastructure.', 'Business District', 3500.00, 'Rent', 4.3, 6, '550e8400-e29b-41d4-a716-446655440007', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440011', 'Country Estate', 'Magnificent 6-bedroom estate with 10 acres of land, horse stables, and private lake. Ultimate luxury living.', 'Countryside', 2500000.00, 'Sale', 4.9, 5, '550e8400-e29b-41d4-a716-446655440001', NOW(), NOW()),
('660e8400-e29b-41d4-a716-446655440012', 'Student Housing', 'Affordable 1-bedroom apartment perfect for students, close to university campus and public transportation.', 'University Area', 800.00, 'Rent', 3.8, 22, '550e8400-e29b-41d4-a716-446655440002', NOW(), NOW());

-- Insert sample property images (using placeholder URLs)
INSERT INTO property_image (id, url, property_id) VALUES
('770e8400-e29b-41d4-a716-446655440001', 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440001'),
('770e8400-e29b-41d4-a716-446655440002', 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440001'),
('770e8400-e29b-41d4-a716-446655440003', 'https://images.unsplash.com/photo-1570129477492-45c003edd2be?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440002'),
('770e8400-e29b-41d4-a716-446655440004', 'https://images.unsplash.com/photo-1570129477492-45c003edd2be?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440002'),
('770e8400-e29b-41d4-a716-446655440005', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440003'),
('770e8400-e29b-41d4-a716-446655440006', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440003'),
('770e8400-e29b-41d4-a716-446655440007', 'https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440004'),
('770e8400-e29b-41d4-a716-446655440008', 'https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440004'),
('770e8400-e29b-41d4-a716-446655440009', 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440005'),
('770e8400-e29b-41d4-a716-446655440010', 'https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440005'),
('770e8400-e29b-41d4-a716-446655440011', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440006'),
('770e8400-e29b-41d4-a716-446655440012', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440006'),
('770e8400-e29b-41d4-a716-446655440013', 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440007'),
('770e8400-e29b-41d4-a716-446655440014', 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440007'),
('770e8400-e29b-41d4-a716-446655440015', 'https://images.unsplash.com/photo-1570129477492-45c003edd2be?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440008'),
('770e8400-e29b-41d4-a716-446655440016', 'https://images.unsplash.com/photo-1570129477492-45c003edd2be?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440008'),
('770e8400-e29b-41d4-a716-446655440017', 'https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440009'),
('770e8400-e29b-41d4-a716-446655440018', 'https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440009'),
('770e8400-e29b-41d4-a716-446655440019', 'https://images.unsplash.com/photo-1497366216548-37526070297c?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440010'),
('770e8400-e29b-41d4-a716-446655440020', 'https://images.unsplash.com/photo-1497366216548-37526070297c?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440010'),
('770e8400-e29b-41d4-a716-446655440021', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440011'),
('770e8400-e29b-41d4-a716-446655440022', 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440011'),
('770e8400-e29b-41d4-a716-446655440023', 'https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440012'),
('770e8400-e29b-41d4-a716-446655440024', 'https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=800&h=600&fit=crop', '660e8400-e29b-41d4-a716-446655440012'); 