package services;

import models.StockMarket;
import models.Transaction;
import repositories.StockMarketRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockMarketService {
    private List<StockMarket> stockMarkets;

    public StockMarketService() {
        this.stockMarkets = StockMarketRepository.readFile();
    }

    public List<StockMarket> getAllStockMarkets() {
        return stockMarkets;
    }

    public StockMarket getStockByTicker(String ticker) {
        for (StockMarket stock : stockMarkets) {
            if (stock.getTicker().equalsIgnoreCase(ticker)) {
                return stock;
            }
        }
        return null;
    }

    public Map<String, Double> calculateSectorDistribution(List<Transaction> transactions) {
        Map<String, Double> distribution = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.getOrderType().equalsIgnoreCase("BUY")) {
                StockMarket stock = getStockByTicker(t.getTicker());
                if (stock != null) {
                    String sector = stock.getSector();
                    double value = t.getQuantity() * stock.getPrice();
                    distribution.put(sector, distribution.getOrDefault(sector, 0.0) + value);
                }
            }
        }

        return distribution;
    }

    public Map<String, Double> calculateStockDistribution(List<Transaction> transactions) {
        Map<String, Double> distribution = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.getOrderType().equalsIgnoreCase("BUY")) {
                StockMarket stock = getStockByTicker(t.getTicker());
                if (stock != null) {
                    double value = t.getQuantity() * stock.getPrice();
                    distribution.put(stock.getName(), distribution.getOrDefault(stock.getName(), 0.0) + value);
                }
            }
        }

        return distribution;
    }
}