package ui;

import controllers.AdminController;

import java.util.Scanner;

public class AdminUI {
    private final AdminController adminController;

    public AdminUI(AdminController adminController) {
        this.adminController = adminController;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        String valg;

        do {
            System.out.println("\n👤 Admin-menu:");
            System.out.println("1. Vis rangliste over brugere");
            System.out.println("2. Vis samlet porteføljeværdi");
            System.out.println("3. Vis fordeling pr. sektor");
            System.out.println("4. Vis fordeling pr. aktie");
            System.out.println("0. Log ud");

            System.out.print("Vælg: ");
            valg = scanner.nextLine();

            switch (valg) {
                case "1" -> adminController.showUserRankings();
                case "2" -> adminController.showTotalPortfolioOverview();
                case "3" -> adminController.showSectorDistribution();
                case "4" -> adminController.showStockDistribution();
                case "0" -> System.out.println("Logger ud...");
                default -> System.out.println("Ugyldigt valg, prøv igen.");
            }

        } while (!valg.equals("0"));
    }
}