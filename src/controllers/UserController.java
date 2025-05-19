package controllers;

import models.StockMarket;
import models.Transaction;
import models.User;
import repositories.StockMarketRepository;
import repositories.TransactionRepository;
import repositories.UserRepository;
import services.StockMarketService;
import services.TransactionService;
import services.UserService;
import ui.UserUI;

import java.util.List;

public class UserController {
    /*
    UserController håndterer køb/salg, se portefølje, transaktion historik.
     */
    private StockMarketRepository stockMarketRepository;
    private UserRepository userRepository;
    private TransactionRepository transactionRepository;

    private StockMarketService stockMarketService;
    private UserService userService;
    private TransactionService transactionService;

    private User user;
    private int userId;

    private UserUI userUI;

    public UserController(String email) {
        this.stockMarketRepository = new StockMarketRepository();
        this.userRepository = new UserRepository();
        this.transactionRepository = new TransactionRepository();
        this.userService = new UserService(userRepository);

        this.user= userService.getUserData(email);
        this.userId= user.getUserID();

        this.stockMarketService = new StockMarketService(stockMarketRepository);
        this.transactionService=new TransactionService(transactionRepository);

        this.userUI=new UserUI(this);

    }

    // public void buyStock method

    //public void sellStock method


    //view portfolio
    public void viewPortfolio() {
        List<Transaction> transactions = getUserStocks();
        List<StockMarket> stocks = stockMarketService.getStockMarket();

        System.out.println("\n-------------------------------");
        System.out.println("           YOUR PORTFOLIO           ");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("| %-10s | %-6s | %-10s | %-12s |\n",
                "Ticker", "Antal", "Pris (DKK)", "Værdi (DKK)");
        System.out.println("-------------------------------------------------------------");

        double total = 0;
        boolean hasHoldings = false;

        for (StockMarket stockMarket : stocks) {
            String ticker = stockMarket.getTicker();
            int amount = 0;

            for (Transaction t : transactions) {
                if (t.getTicker().equalsIgnoreCase(ticker)) {
                    amount += t.getOrderType() ? t.getQuantity() : -t.getQuantity();
                }
            }

            if (amount > 0) {
                double value = stockMarket.getPrice() * amount;
                total += value;
                hasHoldings = true;

                System.out.printf("| %-10s | %-6d | %-10.2f | %-12.2f |\n",
                        ticker, amount, stockMarket.getPrice(), value);
            }
        }

        if (!hasHoldings) {
            System.out.println("| You have no stocks in this stock market |.                     |");
        }

        System.out.println("-------------------------------------------------------------");
        System.out.printf("| %-29s Total: %-12.2f DKK |\n", "", total);
        System.out.println("-------------------------------------------------------------\n");
    }


    //view transaction history
    public List<Transaction> getUserStocks(){
        return transactionService.getStocksByID(userId);
    }
    public void viewTransactionHistory(){
        List<Transaction> transactions = getUserStocks();
        if (transactions.isEmpty()){
            System.out.println("No transactions found");
            return;
        }

        System.out.println("\n------------------------------");
        System.out.println("        TRANSACTIONS HISTORY        ");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("| %-3s | %-12s | %-10s | %-6s | %-6s | %-10s |\n",
                "ID", "Dato", "Ticker", "Type", "Antal", "Pris (DKK)");
        System.out.println("---------------------------------------------------------------");


        for (Transaction t : transactions) {
            String type = t.getOrderType() ? "BUY" : "SELL";
            System.out.printf("| %-3d | %-12s | %-10s | %-6s | %-6d | %-10.2f |\n",
                    t.getId(), t.getDate(), t.getTicker(), type, t.getQuantity(), t.getPrice());
        }

        System.out.println("---------------------------------------------------------------");
    }


    public StockMarketRepository getStockMarketRepository() {
        return stockMarketService.getStockMarketRepository();
    }

    public List<StockMarket> getStockMarket(){
        return stockMarketService.getStockMarket();
    }


    public void start() {
        userUI.start();
    }
}
