package controllers;

import models.User;
import services.AdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public List<String> getUserRankings() {
        List<String> lines = new ArrayList<>();
        List<User> rankedUsers = adminService.getUsersSortedByPortfolio();
        Map<Integer, Double> portfolioValues = adminService.getPortfolioValues();

        for (int i = 0; i < rankedUsers.size(); i++) {
            User user = rankedUsers.get(i);
            int userId = user.getUserID();
            double value = portfolioValues.getOrDefault(userId, 0.0);
            lines.add((i + 1) + ". " + user.getFullName() + " – " + String.format("%.2f DKK", value));
        }

        return lines;
    }

    public double getTotalPortfolioValue() {
        return adminService.getTotalPortfolioValue();
    }

    public Map<String, Double> getSectorDistribution() {
        return adminService.getSectorDistribution();
    }

    public Map<String, Double> getStockDistribution() {
        return adminService.getStockDistribution();
    }
}