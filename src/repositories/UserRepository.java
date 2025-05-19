package repositories;

import models.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    //User repository skal kunne læse of skrive til users.cvs
    // med brug af scanner eller bufferedreader
    private final String path = "src/repositories/users.csv";
    private final List<User> users= new ArrayList<>();

    public void readFile() {
    users.clear();
    try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
    String line = reader.readLine();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        while((line = reader.readLine()) != null) {
    String[] data = line.split(";");

    int id = Integer.parseInt(data[0]);
    String name = data[1];
    String email = data[2];
    LocalDate birthDate = LocalDate.parse(data[3], formatter);
    double balance = Double.parseDouble(data[4]);
    LocalDate createdAt = LocalDate.parse(data[5], formatter);
    LocalDate updatedAt = LocalDate.parse(data[6], formatter);


    User user = new User(id, name, email, birthDate, balance, createdAt, updatedAt);
    users.add(user);

    }

    } catch (Exception e) {
        System.out.println("Error reading file"+e.getMessage());
    }
    }

    public void addNewUser(String name, String email, String birthDate, double balance) {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(path,true));

            int newId = users.size() +1;
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String line= newId + ";" + name+ ";"+ email+";"+birthDate+";"+balance+";"+today+";"+today;

            writer.newLine();
            writer.write(line);
            writer.close();

            readFile();

        }catch (Exception e) {
            System.out.println("Error writing new user"+e.getMessage());
        }
    }


    public List<User> getUsers() {
        return users;
    }
}

