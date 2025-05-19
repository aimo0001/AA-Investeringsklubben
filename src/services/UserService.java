package services;

import models.User;
import repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

public class UserService {

    private UserRepository userRepo;


    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
        userRepo.readFile();
        /*
    UserService skal håndtere login, hente brugerdata, opdatere balance osv.
     */
        List<User> users = userRepo.getUsers();
    }


    public User getUserData(String email) {
        for(User u : userRepo.getUsers()) {
            if(u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        System.out.println("User not found"+email);
        return null;
    }

}
