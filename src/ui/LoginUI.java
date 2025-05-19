package ui;

import controllers.AdminController;
import controllers.UserController;
import models.User;
import services.AdminService;
import services.StockMarketService;
import services.TransactionService;
import services.UserService;

import java.util.Scanner;

public class LoginUI {
    private final UserService userService;
    private final TransactionService transactionService;
    private final StockMarketService stockMarketService;

    public LoginUI() {
        this.stockMarketService = new StockMarketService();
        this.transactionService = new TransactionService(stockMarketService);
        this.userService = new UserService();
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        String valg;

        while (true) {
            System.out.println("\nVelkommen til Investeringsklubben");
            System.out.println("1. Log ind");
            System.out.println("2. Opret bruger");
            System.out.println("0. Afslut");
            System.out.print("Vælg: ");
            valg = scanner.nextLine();

            switch (valg) {
                case "1" -> login(scanner);
                case "2" -> opretBruger(scanner);
                case "0" -> {
                    System.out.println("Farvel!");
                    return;
                }
                default -> System.out.println("Ugyldigt valg.");
            }
        }
    }

    private void login(Scanner scanner) {
        while (true) {
            System.out.print("Indtast fulde navn (eller 0 for at gå tilbage): ");
            String navn = scanner.nextLine().trim();

            if (navn.equals("0")) return;

            if (navn.equalsIgnoreCase("admin")) {
                AdminService adminService = new AdminService(userService, transactionService, stockMarketService);
                AdminController adminController = new AdminController(adminService);
                AdminUI adminUI = new AdminUI(adminController);
                adminUI.show();
                return;
            }

            User bruger = userService.login(navn);
            if (bruger != null) {
                UserController userController = new UserController(bruger, transactionService, stockMarketService);
                UserUI userUI = new UserUI(userController);
                userUI.show();
                return;
            } else {
                System.out.println("Bruger ikke fundet. Prøv igen eller tast 0 for at gå tilbage.");
            }
        }
    }

    private void opretBruger(Scanner scanner) {
        while (true) {
            System.out.print("Fuldt navn (eller 0 for at gå tilbage): ");
            String navn = scanner.nextLine().trim();
            if (navn.equals("0")) return;

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Fødselsdato (dd-mm-yyyy): ");
            String fødselsdato = scanner.nextLine().trim();

            User ny = userService.opretBruger(navn, email, fødselsdato);
            if (ny != null) {
                System.out.println("Bruger oprettet. Du kan nu logge ind.");
                return;
            } else {
                System.out.println("Brugernavnet er allerede i brug. Prøv igen eller tast 0 for at gå tilbage.");
            }
        }
    }
}