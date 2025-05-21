package ui;

import controllers.AdminController;
import controllers.UserController;
import models.User;
import services.AdminService;
import services.StockMarketService;
import services.TransactionService;
import services.UserService;

import javax.swing.*;
import java.awt.*;

public class LoginSwing {
    private static UserService userService = new UserService();
    private static StockMarketService stockMarketService = new StockMarketService();
    private static TransactionService transactionService = new TransactionService(stockMarketService);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Investesteringsklubben - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(5, 1));

        JTextField nameField = new JTextField();
        frame.add(new JLabel("Indtast navn:"));
        frame.add(nameField);

        JLabel feedback = new JLabel("");
        frame.add(feedback);

        JButton loginButton = new JButton("Log ind");
        frame.add(loginButton);

        JButton createButton = new JButton("Opret bruger");
        frame.add(createButton);

        loginButton.addActionListener(e -> {
            String navn = nameField.getText().trim();

            if (navn.equalsIgnoreCase("admin")) {
                feedback.setText("Admin-login succesfuld.");

                AdminController adminController = new AdminController(
                        new AdminService(userService, transactionService, stockMarketService)
                );
                new AdminSwingUI(adminController);
            } else {
                User bruger = userService.login(navn);
                if (bruger != null) {
                    feedback.setText("Login OK – velkommen, " + bruger.getFullName());

                    UserController controller = new UserController(bruger, transactionService, stockMarketService);
                    new UserSwingUI(bruger, controller);
                } else {
                    feedback.setText("Bruger ikke fundet.");
                }
            }
        });

        createButton.addActionListener(e -> {
            String navn = nameField.getText().trim();

            if (navn.isEmpty()) {
                feedback.setText("Navn kan ikke være tomt.");
                return;
            }

            String email = JOptionPane.showInputDialog(frame, "Indtast email:");
            String fødselsdato = JOptionPane.showInputDialog(frame, "Fødselsdato (dd-mm-yyyy):");

            if (email != null && fødselsdato != null) {
                User ny = userService.opretBruger(navn, email, fødselsdato);
                if (ny != null) {
                    feedback.setText("Bruger oprettet! Du kan nu logge ind.");
                } else {
                    feedback.setText("Navn allerede i brug.");
                }
            } else {
                feedback.setText("Brugeroprettelse annulleret.");
            }
        });

        frame.setVisible(true);
    }
}