package controllers;

import models.User;
import services.AdminService;

import java.util.List;
import java.util.Map;

public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public void showUserRankings() {
        List<User> rankedUsers = adminService.getUsersSortedByPortfolio();
        Map<Integer, Double> portfolioValues = adminService.getPortfolioValues();

        System.out.println("Rangliste over brugere:");
        for (int i = 0; i < rankedUsers.size(); i++) {
            User user = rankedUsers.get(i);
            int userId = user.getUserID();
            double value = portfolioValues.getOrDefault(userId, 0.0);
            System.out.printf("%d. %s – %.2f DKK%n", i + 1, user.getFullName(), value);
        }
    }

    public void showTotalPortfolioOverview() {
        double totalValue = adminService.getTotalPortfolioValue();
        System.out.printf("Samlet porteføljeværdi for alle brugere: %.2f DKK%n", totalValue);
    }

    public void showSectorDistribution() {
        Map<String, Double> sectorDist = adminService.getSectorDistribution();

        System.out.println("Fordeling af investeringer pr. sektor:");
        for (Map.Entry<String, Double> entry : sectorDist.entrySet()) {
            System.out.printf("- %s: %.2f DKK%n", entry.getKey(), entry.getValue());
        }
    }

    public void showStockDistribution() {
        Map<String, Double> stockDist = adminService.getStockDistribution();

        System.out.println("Fordeling af investeringer pr. aktie:");
        for (Map.Entry<String, Double> entry : stockDist.entrySet()) {
            System.out.printf("- %s: %.2f DKK%n", entry.getKey(), entry.getValue());
        }
    }
}