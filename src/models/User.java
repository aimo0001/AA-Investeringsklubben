package models;

import java.time.LocalDate;

public class User {
    private int userID;
    private String fullName;
    private String email;
    private LocalDate birthDate;
    private double balance;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public User(int userID, String fullName, String email, LocalDate birthDate, double balance, LocalDate createdAt, LocalDate updatedAt) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getUserID() {
        return userID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}