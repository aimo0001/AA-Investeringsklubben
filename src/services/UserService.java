package services;

import models.User;
import repositories.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserService {
    private List<User> users;

    public UserService() {
        this.users = UserRepository.loadUsers();
    }

    public User login(String fullName) {
        for (User user : users) {
            if (user.getFullName().equalsIgnoreCase(fullName)) {
                return user;
            }
        }
        return null;
    }

    public User opretBruger(String navn, String email, String fødselsdato) {
        try {
            int id = findFriId();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate birthDate = LocalDate.parse(fødselsdato, formatter);

            User ny = new User(id, navn, email, birthDate, 100000.00, LocalDate.now(), LocalDate.now());
            users.add(ny);
            saveAllUsers();
            return ny;
        } catch (Exception e) {
            System.out.println("Fejl ved oprettelse af bruger: " + e.getMessage());
            return null;
        }
    }

    private int findFriId() {
        int id = 1;
        boolean idOptaget;
        do {
            idOptaget = false;
            for (User user : users) {
                if (user.getUserID() == id) {
                    idOptaget = true;
                    id++;
                    break;
                }
            }
        } while (idOptaget);
        return id;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void updateBalance(User updatedUser, double newBalance) {
        for (User user : users) {
            if (user.getUserID() == updatedUser.getUserID()) {
                user.setBalance(newBalance);
                user.setUpdatedAt(LocalDate.now());
                break;
            }
        }
        saveAllUsers();
    }

    public void saveAllUsers() {
        UserRepository.saveUsers(users);
    }
}