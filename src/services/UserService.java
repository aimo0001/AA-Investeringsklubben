package services;

import models.User;
import repositories.UserRepository;

import java.util.List;

public class UserService {

    /*
    UserService skal håndtere login, hente brugerdata, opdatere balance osv.
     */
    private List<User> users;
    private UserRepository userRepo;
    /*

    public UserService(UserRepository) {
        this.userRepo = userRepo;
        userRepo.readFile();
        users = userRepo.getUsers();
    }

     */


}
