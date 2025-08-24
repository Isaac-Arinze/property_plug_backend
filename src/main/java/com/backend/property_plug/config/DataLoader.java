package com.backend.property_plug.config;

import com.backend.property_plug.entity.Property;
import com.backend.property_plug.entity.PropertyImage;
import com.backend.property_plug.entity.User;
import com.backend.property_plug.entity.UserType;
import com.backend.property_plug.repository.PropertyImageRepository;
import com.backend.property_plug.repository.PropertyRepository;
import com.backend.property_plug.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
@Profile("dev") // Only run in development profile
public class DataLoader {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyImageRepository propertyImageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            // Only load data if no users exist
            if (userRepository.count() == 0) {
                loadDummyData();
            }
        };
    }

    private void loadDummyData() {
        System.out.println("Loading dummy data for development...");

        // Create users
        List<User> users = createUsers();
        userRepository.saveAll(users);

        // Create properties
        List<Property> properties = createProperties(users);
        propertyRepository.saveAll(properties);

        // Create property images
        List<PropertyImage> images = createPropertyImages(properties);
        propertyImageRepository.saveAll(images);

        System.out.println("Dummy data loaded successfully!");
        System.out.println("Users created: " + users.size());
        System.out.println("Properties created: " + properties.size());
        System.out.println("Images created: " + images.size());
        
        // Print login credentials for testing
        System.out.println("\n=== TEST LOGIN CREDENTIALS ===");
        System.out.println("All users have password: password123");
        System.out.println("Property Owners:");
        users.stream()
            .filter(u -> u.getUserType() == UserType.PROPERTY_OWNER)
            .forEach(u -> System.out.println("  - " + u.getEmail()));
        System.out.println("Property Tenants:");
        users.stream()
            .filter(u -> u.getUserType() == UserType.PROPERTY_TENANT)
            .forEach(u -> System.out.println("  - " + u.getEmail()));
        System.out.println("================================");
    }

    private List<User> createUsers() {
        return Arrays.asList(
            createUser("John Smith", "john.smith@example.com", "+1234567890", UserType.PROPERTY_OWNER),
            createUser("Sarah Johnson", "sarah.johnson@example.com", "+1234567891", UserType.PROPERTY_OWNER),
            createUser("Mike Wilson", "mike.wilson@example.com", "+1234567892", UserType.PROPERTY_OWNER),
            createUser("Emily Davis", "emily.davis@example.com", "+1234567893", UserType.PROPERTY_TENANT),
            createUser("David Brown", "david.brown@example.com", "+1234567894", UserType.PROPERTY_TENANT),
            createUser("Lisa Anderson", "lisa.anderson@example.com", "+1234567895", UserType.PROPERTY_OWNER),
            createUser("Robert Taylor", "robert.taylor@example.com", "+1234567896", UserType.PROPERTY_OWNER),
            createUser("Jennifer White", "jennifer.white@example.com", "+1234567897", UserType.PROPERTY_TENANT)
        );
    }

    private User createUser(String fullName, String email, String phoneNumber, UserType userType) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(passwordEncoder.encode("password123"));
        user.setUserType(userType);
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    private List<Property> createProperties(List<User> users) {
        List<User> owners = users.stream()
            .filter(u -> u.getUserType() == UserType.PROPERTY_OWNER)
            .toList();

        return Arrays.asList(
            createProperty("Modern Downtown Apartment", 
                "Beautiful 2-bedroom apartment in the heart of downtown with city views, modern amenities, and walking distance to restaurants and shops.",
                "Downtown City Center", 2500.0, "Rent", 4.5, 12, owners.get(0)),
            createProperty("Luxury Beachfront Villa",
                "Stunning 4-bedroom villa with direct beach access, private pool, and panoramic ocean views. Perfect for families or vacation rentals.",
                "Beachfront Paradise", 850000.0, "Sale", 4.8, 8, owners.get(0)),
            createProperty("Cozy Suburban House",
                "Charming 3-bedroom house in quiet suburban neighborhood with large backyard, garage, and excellent schools nearby.",
                "Suburban Heights", 450000.0, "Sale", 4.2, 15, owners.get(1)),
            createProperty("Urban Loft Space",
                "Industrial-style loft with high ceilings, exposed brick, and modern appliances. Perfect for young professionals.",
                "Arts District", 1800.0, "Rent", 4.3, 9, owners.get(1)),
            createProperty("Mountain Cabin Retreat",
                "Rustic 2-bedroom cabin surrounded by nature with hiking trails, fireplace, and stunning mountain views.",
                "Mountain Valley", 320000.0, "Sale", 4.6, 11, owners.get(2)),
            createProperty("Family Townhouse",
                "Spacious 4-bedroom townhouse with community amenities including pool, gym, and playground. Great for families.",
                "Family Community", 2800.0, "Rent", 4.4, 18, owners.get(2)),
            createProperty("Historic City Home",
                "Beautifully restored 3-bedroom historic home with original features, modern updates, and prime city location.",
                "Historic District", 650000.0, "Sale", 4.7, 14, owners.get(3)),
            createProperty("Waterfront Condo",
                "Luxurious 2-bedroom condo with marina views, balcony, and access to boat dock. Perfect for water enthusiasts.",
                "Marina Bay", 2100.0, "Rent", 4.5, 13, owners.get(3)),
            createProperty("Garden Studio Apartment",
                "Charming studio apartment with private garden, updated kitchen, and walking distance to public transportation.",
                "Garden District", 1200.0, "Rent", 4.1, 7, owners.get(4)),
            createProperty("Modern Office Space",
                "Professional office space with meeting rooms, reception area, and modern technology infrastructure.",
                "Business District", 3500.0, "Rent", 4.3, 6, owners.get(4)),
            createProperty("Country Estate",
                "Magnificent 6-bedroom estate with 10 acres of land, horse stables, and private lake. Ultimate luxury living.",
                "Countryside", 2500000.0, "Sale", 4.9, 5, owners.get(0)),
            createProperty("Student Housing",
                "Affordable 1-bedroom apartment perfect for students, close to university campus and public transportation.",
                "University Area", 800.0, "Rent", 3.8, 22, owners.get(1))
        );
    }

    private Property createProperty(String title, String description, String location, Double price, 
                                  String type, Double rating, Integer reviewsCount, User owner) {
        Property property = new Property();
        property.setTitle(title);
        property.setDescription(description);
        property.setLocation(location);
        property.setPrice(price);
        property.setType(type);
        property.setRating(rating);
        property.setReviewsCount(reviewsCount);
        property.setOwner(owner);
        return property;
    }

    private List<PropertyImage> createPropertyImages(List<Property> properties) {
        // Sample image URLs from Unsplash
        String[] imageUrls = {
            "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800&h=600&fit=crop",
            "https://images.unsplash.com/photo-1570129477492-45c003edd2be?w=800&h=600&fit=crop",
            "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800&h=600&fit=crop",
            "https://images.unsplash.com/photo-1449824913935-59a10b8d2000?w=800&h=600&fit=crop",
            "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=800&h=600&fit=crop",
            "https://images.unsplash.com/photo-1497366216548-37526070297c?w=800&h=600&fit=crop"
        };

        List<PropertyImage> images = new ArrayList<>();
        int imageIndex = 0;

        for (Property property : properties) {
            // Add 2 images per property
            for (int i = 0; i < 2; i++) {
                PropertyImage image = new PropertyImage();
                image.setUrl(imageUrls[imageIndex % imageUrls.length]);
                image.setProperty(property);
                images.add(image);
                imageIndex++;
            }
        }

        return images;
    }
} 