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

        } while (!valg.equals("0"));
    }
}