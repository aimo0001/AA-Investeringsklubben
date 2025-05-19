package models;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private int userId;
    private LocalDate date;
    private String ticker;
    private double price;
    private boolean orderType;
    private int quantity;

    public Transaction(int id, int userId, LocalDate date, String ticker, double price, boolean orderType, int quantity) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.ticker = ticker;
        this.price = price;
        this.orderType = orderType;
        this.quantity = quantity;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}

    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}

    public String getTicker() {
        return ticker;

    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString(){
        return "Transaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", ticker='" + ticker + '\'' +
                ", price=" + price +
                ", orderType=" + (orderType ? "BUY" : "SELL") +
                ", quantity=" + quantity +
                '}';
    }

    public boolean getOrderType() {
        return orderType;
    }
}
