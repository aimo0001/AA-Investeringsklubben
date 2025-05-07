package models;

import java.time.LocalDate;

public class User {
    private int userID;
    private String fullName;
    private String email;
    private LocalDate birthDate;
    private double  balance;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public User(int userID, String fullName, String email, String birthDate, double balance, LocalDate createdAt, LocalDate updatedAt) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.birthDate=LocalDate.parse(birthDate);
        this.balance = balance;
        this.createdAt = LocalDate.parse(createdAt.toString());
        this.updatedAt = LocalDate.parse(updatedAt.toString());
    }


    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }


    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }


    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }


    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String toString() {
        return "User{" + "userID=" + userID + ", fullName=" + fullName + ", email=" + email + ", birthDate=" + birthDate + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
}
