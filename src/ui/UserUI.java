package ui;

import controllers.UserController;
import models.StockMarket;

import java.util.List;
import java.util.Scanner;

public class UserUI {
    /*
    Viser køb/salg, portefølje, transaktion historik.
     */

    private final UserController userController;

    public UserUI(UserController userController) {
        this.userController = userController;

    }

    public void start(){
        while (true) {
            System.out.println("""
                Velkommen til InvesteringsKlubben
                -------------------------------
                1. Se aktiemarked
                2. Se portefølje
                3. Se transaktionshistorik
                9. Afslut program
                -------------------------------
                Indtast dit valg:       
                """);
        int choice = new Scanner(System.in).nextInt();

        switch (choice) {
            case 1 -> showAllStocks();
            case 2 -> userController.viewPortfolio();
            case 3 -> userController.viewTransactionHistory();
            case 9 -> {
                System.out.println("Luk ned");
                return;
            }
            default -> System.out.println("Invalid choice");
        }
        }
    }

    public void showAllStocks(){
        List<StockMarket> stocks = userController.getStockMarket();
        System.out.println("\n--- AKTIEMARKED ---");
        for (StockMarket stock : stocks) {
            System.out.println(stock);
        }
        System.out.println();
    }



    public void showTransactionHistory() {
        userController.viewTransactionHistory();
    }


    public void showPortfolio() {
        userController.viewPortfolio();
    }


}

