package services;

import models.StockMarket;
import repositories.StockMarketRepository;

import java.util.List;

public class StockMarketService {
    /*
    Skal give liste over aktier og finde aktiepriser.
     */
    private StockMarketRepository stockMarketRepository;
    private List<StockMarket> stockMarkets;

    public StockMarketService(StockMarketRepository stockMarketRepository){
        this.stockMarketRepository = stockMarketRepository;
        stockMarkets = stockMarketRepository.getStockMarket();
    }

    public StockMarketRepository getStockMarketRepository() {
        return stockMarketRepository;
    }

}
