# Database Testing Guide

This document explains how to run and verify the database functionality for the Hotel Management System.

## Overview

The application uses H2 in-memory database for both development and testing. The database tests verify:

1. **Database Connection** - H2 database connectivity
2. **CRUD Operations** - Create, Read, Update, Delete operations
3. **Data Persistence** - Data saving and retrieval
4. **Service Layer Integration** - Business logic with database
5. **Custom Queries** - Repository custom query methods

## Test Files Created

### 1. DatabaseConfigurationTest.java
- Tests basic database connectivity
- Verifies table creation
- Checks repository injection

### 2. RoomRepositoryDatabaseTest.java
- Tests repository layer database operations
- Verifies CRUD operations with TestEntityManager
- Tests custom query methods
- Uses @DataJpaTest for focused testing

### 3. DatabaseIntegrationTest.java
- Full integration tests with service layer
- Tests complete application context
- Verifies end-to-end database operations

### 4. DatabaseTestRunner.java
- Command-line test runner
- Manual verification of database operations
- Real-time output of test results

## How to Run Tests

### Option 1: Run All Tests
```bash
cd E:\OOPG2\complete
mvnw test
```

### Option 2: Run Specific Test Classes
```bash
# Database configuration test
mvnw test -Dtest=DatabaseConfigurationTest

# Repository-specific tests
mvnw test -Dtest=RoomRepositoryDatabaseTest

# Integration tests
mvnw test -Dtest=DatabaseIntegrationTest
```

### Option 3: Run Database Test Runner
```bash
mvnw exec:java -Dexec.mainClass="com.g2.hotelm.test.DatabaseTestRunner" -Dexec.classpathScope=test
```

### Option 4: Use Test Script
Run the provided batch script:
```bash
test-database.bat
```

## Database Configuration

### Development (application.properties)
```properties
spring.datasource.url=jdbc:h2:mem:hoteldb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

### Testing (application-test.properties)
```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

## What the Tests Verify

### 1. Database Connectivity
- H2 database is properly configured
- DataSource and JdbcTemplate are available
- Basic SQL queries work

### 2. Table Creation
- JPA creates the `rooms` table automatically
- All required columns exist
- Primary key and constraints are set

### 3. CRUD Operations
- **Create**: Save new rooms to database
- **Read**: Retrieve rooms by ID and find all
- **Update**: Modify existing room data
- **Delete**: Remove rooms from database

### 4. Data Persistence
- Data survives flush operations
- Changes are committed to database
- Queries return consistent results

### 5. Business Logic Integration
- Service layer properly uses repository
- Validation rules are enforced
- Transactions work correctly

### 6. Custom Queries
- `findByIsAvailableTrue()` returns only available rooms
- Query methods work with database

## Expected Test Results

When all tests pass, you should see:
- ✓ Database connection established
- ✓ Tables created successfully
- ✓ CRUD operations working
- ✓ Data persistence verified
- ✓ Service integration functional
- ✓ Custom queries operational

## Troubleshooting

### Common Issues

1. **H2 Driver Not Found**
   - Ensure H2 dependency is in pom.xml
   - Check classpath configuration

2. **Table Not Created**
   - Verify JPA entity annotations
   - Check hibernate.ddl-auto setting

3. **Connection Failed**
   - Check H2 database URL format
   - Verify application.properties

4. **Test Data Issues**
   - Ensure test data meets validation constraints
   - Check @Valid annotations on entities

### Debug Tips

1. **Enable SQL Logging**
   ```properties
   spring.jpa.show-sql=true
   logging.level.org.hibernate.SQL=DEBUG
   ```

2. **Access H2 Console**
   - Start the application
   - Go to http://localhost:8080/h2-console
   - Use JDBC URL: `jdbc:h2:mem:hoteldb`

3. **Check Test Output**
   - Maven will show detailed test results
   - Look for stack traces in case of failures

## Manual Verification

You can also manually verify database operations by:

1. Running the main application
2. Accessing the web interface at http://localhost:8080/rooms
3. Adding, editing, and deleting rooms
4. Checking the H2 console to see data persistence

The H2 console allows you to:
- View table structure
- Execute SQL queries
- See actual data stored
- Monitor database operations

## Files Modified/Created

- `src/test/java/com/g2/hotelm/integration/DatabaseIntegrationTest.java`
- `src/test/java/com/g2/hotelm/repository/RoomRepositoryDatabaseTest.java`
- `src/test/java/com/g2/hotelm/database/DatabaseConfigurationTest.java`
- `src/test/java/com/g2/hotelm/test/DatabaseTestRunner.java`
- `src/test/resources/application-test.properties`
- `src/main/resources/application.properties`
- `test-database.bat`

This comprehensive testing suite ensures that the hotel management system properly saves and retrieves data from the local H2 database.
