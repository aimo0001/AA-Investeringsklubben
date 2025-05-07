package models;

public class Transaction {
    private int id;
    private User userID;
    private String ticker;
    private double price;
    private boolean orderType;
    private int quantity;

    public Transaction (String ticker, double price, boolean orderType, int quantity) {
        this.ticker = ticker;
        this.price = price;
        this.orderType = orderType;

    }

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
}
