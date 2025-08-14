package com.g2.hotelm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class HotelManagementApplicationTest {

    @Test
    void contextLoads() {
        // Test that the Spring application context loads successfully
        // This test verifies that all beans can be created and injected properly
    }

    @Test
    void mainMethodRuns() {
        // Test that the main method can be invoked
        // This is a simple smoke test to ensure the application can start
        HotelManagementApplication.main(new String[]{});
    }
}
