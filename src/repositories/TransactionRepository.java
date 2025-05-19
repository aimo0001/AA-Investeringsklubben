package repositories;

import models.Transaction;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionRepository {
    private static final String FILE_PATH = "src/repositories/transactions.csv";

    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            Scanner scanner = new Scanner(new File(FILE_PATH));
            if (scanner.hasNextLine()) scanner.nextLine(); // skip header

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";", -1);

                if (parts.length >= 8) {
                    int id = Integer.parseInt(parts[0].trim());
                    int userId = Integer.parseInt(parts[1].trim());
                    LocalDate date = LocalDate.parse(parts[2].trim(), formatter);
                    String ticker = parts[3].trim();
                    double price = Double.parseDouble(parts[4].trim().replace(",", "."));
                    String currency = parts[5].trim();
                    String orderType = parts[6].trim().toUpperCase();
                    int quantity = Integer.parseInt(parts[7].trim());

                    Transaction transaction = new Transaction(id, userId, date, ticker, price, currency, orderType, quantity);
                    transactions.add(transaction);
                }
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("Fejl ved indlæsning af transactions.csv: " + e.getMessage());
        }

        return transactions;
    }

    // Gem ny transaktion
    public static void saveTransaction(Transaction transaction) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            String line = String.format(
                    "%d;%d;%s;%s;%.2f;%s;%s;%d",
                    transaction.getId(),
                    transaction.getUserId(),
                    transaction.getDate().format(formatter),
                    transaction.getTicker(),
                    transaction.getPrice(),
                    transaction.getCurrency(),
                    transaction.getOrderType().toLowerCase(),
                    transaction.getQuantity()
            );
            writer.write(line + "\n");
        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til transactions.csv: " + e.getMessage());
        }
    }
}