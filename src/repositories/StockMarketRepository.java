package repositories;

import models.StockMarket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class StockMarketRepository {
    private static final String FILE_PATH = "src/repositories/stockMarket.csv";

    public static List<StockMarket> readFile() {
        List<StockMarket> stockMarkets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] split = line.split(";");
                String ticker = split[0];
                String name = split[1];
                String sector = split[2];
                double price = Double.parseDouble(split[3].replace(",", "."));

                StockMarket stock = new StockMarket(ticker, name, sector, price);
                stockMarkets.add(stock);
            }

        } catch (Exception e) {
            System.out.println("Fejl ved læsning af stockMarket.csv: " + e.getMessage());
        }

        return stockMarkets;
    }

    public static StockMarket getStockByTicker(String ticker) {
        for (StockMarket stock : readFile()) {
            if (stock.getTicker().equalsIgnoreCase(ticker)) {
                return stock;
            }
        }
        return null;
    }
}