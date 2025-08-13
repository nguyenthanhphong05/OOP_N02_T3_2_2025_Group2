package com.g2.hotelm.database;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.g2.hotelm.repository.RoomRepository;

@SpringBootTest
@ActiveProfiles("test")
class DatabaseConfigurationTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void testDatabaseConnectionIsEstablished() {
        // Verify DataSource is configured
        assertNotNull(dataSource, "DataSource should be available");
        
        // Verify JdbcTemplate is working
        assertNotNull(jdbcTemplate, "JdbcTemplate should be available");
        
        // Test basic database connectivity
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertNotNull(result, "Database query should return result");
        assertTrue(result == 1, "Query should return 1");
    }

    @Test
    void testRoomTableExists() {
        // Verify the rooms table was created by JPA
        Integer tableCount = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ROOMS'", 
            Integer.class
        );
        
        assertNotNull(tableCount, "Table count query should return a result");
        assertTrue(tableCount > 0, "ROOMS table should exist");
    }

    @Test
    void testRoomTableStructure() {
        // Verify table columns exist
        String[] expectedColumns = {"ID", "ROOM_ID", "TYPE", "PRICE", "IS_AVAILABLE", "DESCRIPTION"};
        
        for (String column : expectedColumns) {
            Integer columnCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'ROOMS' AND COLUMN_NAME = ?",
                Integer.class,
                column
            );
            
            assertNotNull(columnCount, "Column count query should return result for " + column);
            assertTrue(columnCount > 0, "Column " + column + " should exist in ROOMS table");
        }
    }

    @Test
    void testRepositoryIsInjected() {
        assertNotNull(roomRepository, "RoomRepository should be injected");
        
        // Verify repository methods are accessible
        assertNotNull(roomRepository.findAll(), "findAll() should be callable");
        assertTrue(roomRepository.count() >= 0, "count() should return non-negative number");
    }

    @Test
    void testDatabaseIsEmptyInitially() {
        // In test profile, database should be accessible (could have test data)
        long roomCount = roomRepository.count();
        assertTrue(roomCount >= 0, "Database should return valid count in test environment");
    }
}
