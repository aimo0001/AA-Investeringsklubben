package ui;

import controllers.AdminController;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AdminSwingUI {
    private final AdminController controller;

    public AdminSwingUI(AdminController controller) {
        this.controller = controller;

        JFrame frame = new JFrame("Admin - Investorklub");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JLabel header = new JLabel("ADMIN PANEL", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(header, BorderLayout.NORTH);

        JTextArea output = new JTextArea();
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton ranglisteBtn = new JButton("Rangliste");
        JButton samletBtn = new JButton("Samlet værdi");
        JButton sektorBtn = new JButton("Sektorfordeling");
        JButton aktieBtn = new JButton("Aktiefordeling");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(ranglisteBtn);
        buttonPanel.add(samletBtn);
        buttonPanel.add(sektorBtn);
        buttonPanel.add(aktieBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        ranglisteBtn.addActionListener(e -> {
            output.setText("Rangliste:\n");
            controller.getUserRankings().forEach(line -> output.append(line + "\n"));
        });

        samletBtn.addActionListener(e -> {
            double total = controller.getTotalPortfolioValue();
            output.setText("Samlet porteføljeværdi:\n" + String.format("%.2f DKK", total));
        });

        sektorBtn.addActionListener(e -> {
            output.setText("Sektorfordeling:\n");
            for (Map.Entry<String, Double> entry : controller.getSectorDistribution().entrySet()) {
                output.append("- " + entry.getKey() + ": " + String.format("%.2f DKK", entry.getValue()) + "\n");
            }
        });

        aktieBtn.addActionListener(e -> {
            output.setText("Aktiefordeling:\n");
            for (Map.Entry<String, Double> entry : controller.getStockDistribution().entrySet()) {
                output.append("- " + entry.getKey() + ": " + String.format("%.2f DKK", entry.getValue()) + "\n");
            }
        });

        frame.setVisible(true);
    }
}