package models;

public class StockMarket {
    private String ticker;
    private String name;
    private String sector;
    private double price;



    public StockMarket(String ticker, String name, String sector, double price) {
        this.ticker = ticker;
        this.name = name;
        this.sector = sector;
        this.price = price;
    }

    @Override
    public String toString() {
            return "StockMarket{"+ticker + " - " + name + " - " + sector + " - " + price + " DKK" +'}';
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;

    }
    public void setSector(String sector) {
        this.sector = sector;
    }
}
