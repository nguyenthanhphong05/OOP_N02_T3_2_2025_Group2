package test;
import controller.CustomerController;
import model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    // filepath: /workspaces/OOP_N02_T3_2_2025_Group2/src/test/TestCustomerController.java



public class TestCustomerController {
    @Test
    void testAddCustomer() {
        CustomerController controller = new CustomerController();
        Customer customer = new Customer("1", "A", "a@gmail.com", "0123");
        String result = controller.addCustomer(customer);
        assertEquals("redirect:/customers", result);
    }
}