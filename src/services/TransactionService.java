package services;

import models.StockMarket;
import models.Transaction;
import models.User;
import repositories.TransactionRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TransactionService {
    private final List<Transaction> transactions;
    private final StockMarketService stockMarketService;

    public TransactionService(StockMarketService stockMarketService) {
        this.stockMarketService = stockMarketService;
        this.transactions = TransactionRepository.loadTransactions();
    }

    public void recordTransaction(User user, String orderType, String ticker, int quantity, double price) {
        int nextId = transactions.size() + 1;

        Transaction transaction = new Transaction(
                nextId,
                user.getUserID(),
                LocalDate.now(),
                ticker,
                price,
                "DKK",
                orderType.toUpperCase(),
                quantity
        );

        transactions.add(transaction);
        TransactionRepository.saveTransaction(transaction);
    }

    public int getOwnedQuantity(int userId, String ticker) {
        int total = 0;
        for (Transaction t : transactions) {
            if (t.getUserId() == userId && t.getTicker().equalsIgnoreCase(ticker)) {
                if (t.getOrderType().equalsIgnoreCase("BUY")) {
                    total += t.getQuantity();
                } else if (t.getOrderType().equalsIgnoreCase("SELL")) {
                    total -= t.getQuantity();
                }
            }
        }
        return total;
    }

    public void showPortfolioForUser(int userId) {
        Map<String, Integer> holdings = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.getUserId() == userId) {
                holdings.putIfAbsent(t.getTicker(), 0);
                int current = holdings.get(t.getTicker());
                int change = t.getOrderType().equalsIgnoreCase("BUY") ? t.getQuantity() : -t.getQuantity();
                holdings.put(t.getTicker(), current + change);
            }
        }

        System.out.println("Din portefølje:");
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            if (entry.getValue() > 0) {
                StockMarket stock = stockMarketService.getStockByTicker(entry.getKey());
                double price = stock != null ? stock.getPrice() : 0;
                double value = entry.getValue() * price;
                System.out.printf("- %s: %d stk. (%.2f DKK)%n", entry.getKey(), entry.getValue(), value);
            }
        }
    }

    public void showTransactionsForUser(int userId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("Transaktionshistorik:");
        for (Transaction t : transactions) {
            if (t.getUserId() == userId) {
                System.out.printf("%s | %s | %s | %d stk. | %.2f DKK%n",
                        t.getDate().format(formatter),
                        t.getOrderType(),
                        t.getTicker(),
                        t.getQuantity(),
                        t.getPrice());
            }
        }
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public Map<Integer, Double> calculatePortfolioValues() {
        Map<Integer, Map<String, Integer>> userHoldings = new HashMap<>();

        for (Transaction t : transactions) {
            int userId = t.getUserId();
            String ticker = t.getTicker();
            int quantity = t.getOrderType().equalsIgnoreCase("BUY") ? t.getQuantity() : -t.getQuantity();

            userHoldings.putIfAbsent(userId, new HashMap<>());
            Map<String, Integer> holdings = userHoldings.get(userId);
            holdings.put(ticker, holdings.getOrDefault(ticker, 0) + quantity);
        }

        Map<Integer, Double> userValues = new HashMap<>();

        for (Map.Entry<Integer, Map<String, Integer>> userEntry : userHoldings.entrySet()) {
            int userId = userEntry.getKey();
            double totalValue = 0.0;

            for (Map.Entry<String, Integer> stockEntry : userEntry.getValue().entrySet()) {
                String ticker = stockEntry.getKey();
                int quantity = stockEntry.getValue();

                if (quantity > 0) {
                    StockMarket stock = stockMarketService.getStockByTicker(ticker);
                    if (stock != null) {
                        totalValue += quantity * stock.getPrice();
                    }
                }
            }

            userValues.put(userId, totalValue);
        }

        return userValues;
    }
}
