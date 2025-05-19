package services;

import models.Transaction;
import models.User;

import java.util.*;

public class AdminService {

    private final UserService userService;
    private final TransactionService transactionService;
    private final StockMarketService stockMarketService;

    public AdminService(UserService userService, TransactionService transactionService, StockMarketService stockMarketService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.stockMarketService = stockMarketService;
    }

    public double getTotalPortfolioValue() {
        Map<Integer, Double> portfolioValues = transactionService.calculatePortfolioValues();
        return portfolioValues.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    public Map<Integer, Double> getPortfolioValues() {
        return transactionService.calculatePortfolioValues();
    }

    public List<User> getUsersSortedByPortfolio() {
        Map<Integer, Double> values = getPortfolioValues();
        List<User> users = userService.getAllUsers();

        users.sort((u1, u2) -> Double.compare(
                values.getOrDefault(u2.getUserID(), 0.0),
                values.getOrDefault(u1.getUserID(), 0.0)
        ));

        return users;
    }

    public Map<String, Double> getSectorDistribution() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return stockMarketService.calculateSectorDistribution(transactions);
    }

    public Map<String, Double> getStockDistribution() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return stockMarketService.calculateStockDistribution(transactions);
    }
}
