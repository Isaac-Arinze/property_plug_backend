# Testing the Dummy Data Loading

## Quick Test Steps

1. **Start the application in dev mode:**
   ```bash
   cd property-plug
   ./mvnw spring-boot:run -Dspring.profiles.active=dev
   ```

2. **Check the console output** - you should see:
   ```
   Loading dummy data for development...
   Dummy data loaded successfully!
   Users created: 8
   Properties created: 12
   Images created: 24
   
   === TEST LOGIN CREDENTIALS ===
   All users have password: password123
   Property Owners:
     - john.smith@example.com
     - sarah.johnson@example.com
     - mike.wilson@example.com
     - lisa.anderson@example.com
     - robert.taylor@example.com
   Property Tenants:
     - emily.davis@example.com
     - david.brown@example.com
     - jennifer.white@example.com
   ================================
   ```

3. **Test the GET endpoints:**
   
   **Public endpoints (no auth required):**
   ```bash
   # Get all properties for listing
   curl http://localhost:8090/properties/listing
   
   # Get properties with pagination
   curl http://localhost:8090/properties/listing?page=0&size=5
   
   # Get properties by location
   curl http://localhost:8090/properties/listing?location=Downtown
   
   # Get properties by price range
   curl http://localhost:8090/properties/listing?minPrice=1000&maxPrice=3000
   ```

4. **Test authentication endpoints:**
   ```bash
   # Login as a property owner
   curl -X POST http://localhost:8090/api/v1/auth/login \
     -H "Content-Type: application/json" \
     -d '{"email":"john.smith@example.com","password":"password123"}'
   ```

5. **Test protected endpoints with JWT token:**
   ```bash
   # Use the JWT token from the login response
   curl -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     http://localhost:8090/properties/my
   ```

## Expected Results

- **GET /properties/listing** should return 12 properties
- **GET /properties/listing?type=Rent** should return 6 properties
- **GET /properties/listing?type=Sale** should return 6 properties
- **GET /properties/listing?location=Downtown** should return 1 property
- **GET /properties/listing?minPrice=1000&maxPrice=3000** should return properties in that price range

## Troubleshooting

If you don't see the dummy data loading message:
1. Check that `spring.profiles.active=dev` is set
2. Ensure your database is running and accessible
3. Check that the database tables exist
4. Look for any error messages in the console

If you get database errors:
1. Make sure PostgreSQL is running
2. Check your database connection settings in `application-dev.properties`
3. Verify the database `propertyplugdb` exists
4. Check that the user has proper permissions 