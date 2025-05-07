package models;

public class StockMarket {
    private double price;
    private String ticker;

    public StockMarket(double price, String ticker) {
        this.ticker=ticker;
        this.price=price;
    }

    @Override
    public String toString() {
        return "StockMarket{" + "ticker='" + ticker + '\'' + ", price=" + price+'}';
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;

    }
    public String getTicker() {
        return ticker;

    }
    public void setTicker(String ticker) {
        this.ticker = ticker;

    }
}
