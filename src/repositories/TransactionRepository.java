package repositories;

import models.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {
    //TransactionRepository skal læse og skrive til transactions.cvs
    // via Scanner eller Bufferedreader

    public List<Transaction> readFile() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        String path= "src/repositories/transactions.csv";

        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
        String line;
        reader.readLine();

        while((line= reader.readLine()) != null){
            String[] tokens = line.split(";");

            int id = Integer.parseInt(tokens[0]);
            int userId = Integer.parseInt(tokens[1]);
                LocalDate date = LocalDate.parse(tokens[2], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String ticker = tokens[3];
                double price = Double.parseDouble(tokens[4].replace(",","."));
                boolean orderType = tokens[6].equalsIgnoreCase("buy");
                int quantity = Integer.parseInt(tokens[7]);

                Transaction t = new Transaction(id,userId,date,ticker,price,orderType,quantity);
                transactions.add(t);
            }

        } catch(Exception e){
            System.out.println("Error reading file" + e.getMessage());
        }
        return transactions;
    }

}