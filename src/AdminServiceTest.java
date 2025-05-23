import models.User;
import org.junit.jupiter.api.Test;
import services.AdminService;
import services.TransactionService;
import services.UserService;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminServiceTest {

    @Test
    public void testUsersSortedByPortfolioValue() {
        User u1 = new User(1, "Asli", "a@mail.com", LocalDate.of(1990, 1, 1), 0, LocalDate.now(), LocalDate.now());
        User u2 = new User(2, "Aisha", "aa@mail.com", LocalDate.of(1990, 1, 1), 0, LocalDate.now(), LocalDate.now());

        List<User> dummyUsers = new ArrayList<>(List.of(u1, u2));
        Map<Integer, Double> dummyValues = Map.of(
                1, 120000.0,
                2, 90000.0
        );

        UserService mockUserService = new UserService() {
            @Override
            public List<User> getAllUsers() {
                return dummyUsers;
            }
        };

        TransactionService mockTransactionService = new TransactionService(null) {
            @Override
            public Map<Integer, Double> calculatePortfolioValues() {
                return dummyValues;
            }
        };

        AdminService adminService = new AdminService(mockUserService, mockTransactionService, null);
        List<User> result = adminService.getUsersSortedByPortfolio();

        assertEquals("Asli", result.get(0).getFullName());
        assertEquals("Aisha", result.get(1).getFullName());
    }

    @Test
    public void testTotalPortfolioValueIsCorrect() {
        Map<Integer, Double> dummyValues = Map.of(
                1, 100000.0,
                2, 50000.0
        );

        TransactionService mockTransactionService = new TransactionService(null) {
            @Override
            public Map<Integer, Double> calculatePortfolioValues() {
                return dummyValues;
            }
        };

        AdminService adminService = new AdminService(null, mockTransactionService, null);
        double total = adminService.getTotalPortfolioValue();

        assertEquals(150000.0, total, 0.01);
    }
}