package ui;

import controllers.UserController;
import models.User;

import javax.swing.*;
import java.awt.*;

public class UserSwingUI {
    private final JFrame frame;
    private final User user;
    private final UserController controller;

    public UserSwingUI(User user, UserController controller) {
        this.user = user;
        this.controller = controller;

        frame = new JFrame("Investor - " + user.getFullName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Velkommen, " + user.getFullName(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(welcomeLabel, BorderLayout.NORTH);

        JTextArea output = new JTextArea();
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton portfolioBtn = new JButton("Se portefølje");
        JButton transactionsBtn = new JButton("Se transaktioner");
        JButton buyBtn = new JButton("Køb aktie");
        JButton sellBtn = new JButton("Sælg aktie");
        JButton logoutBtn = new JButton("Log ud");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.add(portfolioBtn);
        buttonPanel.add(transactionsBtn);
        buttonPanel.add(buyBtn);
        buttonPanel.add(sellBtn);
        buttonPanel.add(logoutBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        portfolioBtn.addActionListener(e -> {
            String tekst = controller.getPortfolioText();
            output.setText("Din portefølje:\n" + tekst);
        });

        transactionsBtn.addActionListener(e -> {
            String tekst = controller.getTransactionHistoryText();
            output.setText("Dine transaktioner:\n" + tekst);
        });

        buyBtn.addActionListener(e -> {
            String ticker = JOptionPane.showInputDialog(frame, "Indtast ticker:");
            if (ticker == null || ticker.isBlank()) return;

            String antalStr = JOptionPane.showInputDialog(frame, "Antal aktier:");
            try {
                int antal = Integer.parseInt(antalStr);
                String svar = controller.buyStock(ticker, antal);
                output.setText(svar);
            } catch (Exception ex) {
                output.setText("Ugyldigt input.");
            }
        });

        sellBtn.addActionListener(e -> {
            String ticker = JOptionPane.showInputDialog(frame, "Indtast ticker:");
            if (ticker == null || ticker.isBlank()) return;

            String antalStr = JOptionPane.showInputDialog(frame, "Antal aktier:");
            try {
                int antal = Integer.parseInt(antalStr);
                String svar = controller.sellStock(ticker, antal);
                output.setText(svar);
            } catch (Exception ex) {
                output.setText("Ugyldigt input.");
            }
        });

        logoutBtn.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }
}