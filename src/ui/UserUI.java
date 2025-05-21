package ui;

import controllers.UserController;

import java.util.Scanner;

public class UserUI {
    private final UserController userController;

    public UserUI(UserController userController) {
        this.userController = userController;
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        String valg;

        System.out.println("Hej, " + userController.getUser().getFullName() + "!");

        do {
            System.out.println("\n📊 Bruger-menu:");
            System.out.println("1. Køb aktier");
            System.out.println("2. Sælg aktier");
            System.out.println("3. Vis portefølje");
            System.out.println("4. Vis transaktionshistorik");
            System.out.println("0. Log ud");
            System.out.print("Vælg: ");
            valg = scanner.nextLine();

            switch (valg) {
                case "1" -> køb(scanner);
                case "2" -> sælg(scanner);
                case "3" -> System.out.println(userController.getPortfolioText());
                case "4" -> System.out.println(userController.getTransactionHistoryText());
                case "0" -> System.out.println("Logger ud...");
                default -> System.out.println("Ugyldigt valg.");
            }

        } while (!valg.equals("0"));
    }

    private void køb(Scanner scanner) {
        System.out.print("Indtast aktie-ticker: ");
        String ticker = scanner.nextLine();
        System.out.print("Antal du vil købe: ");
        int antal = Integer.parseInt(scanner.nextLine());
        String resultat = userController.buyStock(ticker, antal);
        System.out.println(resultat);
    }

    private void sælg(Scanner scanner) {
        System.out.print("Indtast aktie-ticker: ");
        String ticker = scanner.nextLine();
        System.out.print("Antal du vil sælge: ");
        int antal = Integer.parseInt(scanner.nextLine());
        String resultat = userController.sellStock(ticker, antal);
        System.out.println(resultat);
    }
}