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

    public String getPortfolioForUser(int userId) {
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> holdings = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.getUserId() == userId) {
                int change = t.getOrderType().equalsIgnoreCase("BUY") ? t.getQuantity() : -t.getQuantity();
                holdings.put(t.getTicker(), holdings.getOrDefault(t.getTicker(), 0) + change);
            }
        }

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            if (entry.getValue() > 0) {
                StockMarket stock = stockMarketService.getStockByTicker(entry.getKey());
                double price = stock != null ? stock.getPrice() : 0;
                double value = entry.getValue() * price;
                sb.append(String.format("- %s: %d stk. (%.2f DKK)%n", entry.getKey(), entry.getValue(), value));
            }
        }

        return sb.toString();
    }

    public String getTransactionsForUser(int userId) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Transaction t : transactions) {
            if (t.getUserId() == userId) {
                sb.append(String.format("%s | %s | %s | %d stk. | %.2f DKK%n",
                        t.getDate().format(formatter),
                        t.getOrderType(),
                        t.getTicker(),
                        t.getQuantity(),
                        t.getPrice()));
            }
        }

        return sb.toString();
    }

    public String handleBuy(User user, String ticker, int quantity) {
        StockMarket stock = stockMarketService.getStockByTicker(ticker);
        if (stock == null) return "Aktie ikke fundet.";

        double totalPrice = quantity * stock.getPrice();
        if (user.getBalance() < totalPrice) {
            return "Ikke nok penge. Du mangler " + String.format("%.2f DKK", (totalPrice - user.getBalance()));
        }

        user.setBalance(user.getBalance() - totalPrice);
        recordTransaction(user, "BUY", ticker, quantity, stock.getPrice());
        return String.format("Købte %d stk. af %s til %.2f DKK", quantity, ticker, totalPrice);
    }

    public String handleSell(User user, String ticker, int quantity) {
        int owned = getOwnedQuantity(user.getUserID(), ticker);
        if (owned < quantity) {
            return "Du ejer kun " + owned + " stk. af " + ticker;
        }

        StockMarket stock = stockMarketService.getStockByTicker(ticker);
        double total = quantity * stock.getPrice();
        user.setBalance(user.getBalance() + total);
        recordTransaction(user, "SELL", ticker, quantity, stock.getPrice());
        return String.format("Solgte %d stk. af %s for %.2f DKK", quantity, ticker, total);
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