package services;

import models.Transaction;
import repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    /*
    TransactionService skal håndtere køb/salg og opdatere filer.
     */
    private TransactionRepository transactionRepository;
    private List<Transaction> allTransactions;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        this.allTransactions = transactionRepository.readFile();
    }
    public List<Transaction> getStocksByID(int userID) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            if (transaction.getUserId() == userID) {
                result.add(transaction);
            }
        }
        return result;
    }
}
