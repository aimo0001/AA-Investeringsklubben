package controllers;

import models.User;
import services.TransactionService;
import services.StockMarketService;

public class UserController {
    private final User user;
    private final TransactionService transactionService;
    private final StockMarketService stockMarketService;

    public UserController(User user, TransactionService transactionService, StockMarketService stockMarketService) {
        this.user = user;
        this.transactionService = transactionService;
        this.stockMarketService = stockMarketService;
    }

    public String getPortfolioText() {
        return transactionService.getPortfolioForUser(user.getUserID());
    }

    public String getTransactionHistoryText() {
        return transactionService.getTransactionsForUser(user.getUserID());
    }

    public String buyStock(String ticker, int quantity) {
        return transactionService.handleBuy(user, ticker, quantity);
    }

    public String sellStock(String ticker, int quantity) {
        return transactionService.handleSell(user, ticker, quantity);
    }

    public double getBalance() {
        return user.getBalance();
    }

    public String getUserName() {
        return user.getFullName();
    }

    public User getUser() {
        return user;
    }
}