package controllers;

import models.User;
import repositories.UserRepository;

import java.util.List;

public class LoginController {

    public boolean isAdmin(String fullName) {
        return fullName.equalsIgnoreCase("admin");
    }

    public User findUserByName(String fullName) {
        List<User> users = UserRepository.loadUsers();
        for (User user : users) {
            if (user.getFullName().equalsIgnoreCase(fullName)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return UserRepository.loadUsers();
    }
}