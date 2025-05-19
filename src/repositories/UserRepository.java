package repositories;

import models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRepository {
    private static final String FILE_PATH = "src/repositories/users.csv";

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            Scanner scanner = new Scanner(new File(FILE_PATH));

            if (scanner.hasNextLine()) {
                scanner.nextLine(); // spring header over
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";", -1);

                if (parts.length >= 7) {
                    int userID = Integer.parseInt(parts[0].trim());
                    String fullName = parts[1].trim();
                    String email = parts[2].trim();
                    LocalDate birthDate = LocalDate.parse(parts[3].trim(), formatter);
                    double balance = Double.parseDouble(parts[4].trim().replace(",", "."));
                    LocalDate createdAt = LocalDate.parse(parts[5].trim(), formatter);
                    LocalDate updatedAt = LocalDate.parse(parts[6].trim(), formatter);

                    User user = new User(userID, fullName, email, birthDate.toString(), balance, createdAt, updatedAt);
                    users.add(user);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Filen users.csv blev ikke fundet: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Fejl ved indlæsning af brugerdata: " + e.getMessage());
        }

        return users;
    }

    public static void saveUsers(List<User> users) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (FileWriter writer = new FileWriter(FILE_PATH, false)) {
            writer.write("user_id;fullName;email;birthDate;balance;createdAt;updatedAt\n");

            for (User u : users) {
                String line = String.format("%d;%s;%s;%s;%.2f;%s;%s",
                        u.getUserID(),
                        u.getFullName(),
                        u.getEmail(),
                        u.getBirthDate(),
                        u.getBalance(),
                        u.getCreatedAt().format(formatter),
                        u.getUpdatedAt().format(formatter)
                );
                writer.write(line + "\n");
            }
        } catch (Exception e) {
            System.out.println("Fejl ved gemning af brugere: " + e.getMessage());
        }
    }
}