package controllers;

import models.User;
import services.TransactionService;
import services.StockMarketService;
import models.StockMarket;

public class UserController {
    private final User user;
    private final TransactionService transactionService;
    private final StockMarketService stockMarketService;

    public UserController(User user, TransactionService transactionService, StockMarketService stockMarketService) {
        this.user = user;
        this.transactionService = transactionService;
        this.stockMarketService = stockMarketService;
    }

    public void buyStock(String ticker, int quantity) {
        StockMarket stock = stockMarketService.getStockByTicker(ticker);
        if (stock == null) {
            System.out.println("Aktien findes ikke.");
            return;
        }

        double totalPrice = stock.getPrice() * quantity;

        if (user.getBalance() >= totalPrice) {
            user.setBalance(user.getBalance() - totalPrice);
            transactionService.recordTransaction(user, "BUY", ticker, quantity, stock.getPrice());
            System.out.println("Du har købt " + quantity + " stk. af " + stock.getName());
        } else {
            System.out.printf("Du har ikke nok penge. Du mangler %.2f DKK.%n", (totalPrice - user.getBalance()));
        }
    }

    public void sellStock(String ticker, int quantity) {
        StockMarket stock = stockMarketService.getStockByTicker(ticker);
        if (stock == null) {
            System.out.println("Aktien findes ikke.");
            return;
        }

        int owned = transactionService.getOwnedQuantity(user.getUserID(), ticker);
        if (owned < quantity) {
            System.out.printf("Du ejer kun %d stk. af %s. Salg afvist.%n", owned, ticker);
            return;
        }

        double totalPrice = stock.getPrice() * quantity;
        user.setBalance(user.getBalance() + totalPrice);
        transactionService.recordTransaction(user, "SELL", ticker, quantity, stock.getPrice());
        System.out.println("Du har solgt " + quantity + " stk. af " + stock.getName());
    }

    public void viewPortfolio() {
        transactionService.showPortfolioForUser(user.getUserID());
    }

    public void viewTransactionHistory() {
        transactionService.showTransactionsForUser(user.getUserID());
    }

    public void displayStockMarket() {
        stockMarketService.printAllStockMarkets();
    }

    public User getUser() {
        return user;
    }
}
