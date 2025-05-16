package repositories;

import models.StockMarket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class StockMarketRepository {
    //StockMarketRepository skal KUN læse fra stockMarket.

    public List<StockMarket> readFile(){
        List<StockMarket> stockMarkets = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader("src/repositories/stockMarket.csv"))){
            String line = "";
            reader.readLine();

            while ((line=reader.readLine())!=null){
                String [] split = line.split(";");
                String ticker = split[0];
                String name = split[1];
                String sector = split[2];
                double price = Double.parseDouble(split[3].replace(",", "."));

                StockMarket stock =new StockMarket(ticker, name, sector,price);
                stockMarkets.add(stock);
            }

        } catch (Exception e){
            System.out.println("Error reading stockMarket.csv"+e.getMessage());
        }
        return stockMarkets;

    }
/*
    public StockMarketRepository(){
        this.getStockMarket = new ArrayList<>();
        readStockList();
    }
    public void readStockList(){
        getStockMarket.clear();
        try {
            Scanner reader = new Scanner(new File(PATH));
            if (reader.hasNextLine()) {
                reader.nextLine();
            }
            Scanner lineScanner = null;
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");
            }
            String ticker = lineScanner.next();
            String name = lineScanner.next();
            double price = Double.parseDouble(lineScanner.next());
            String sector = lineScanner.next();
        }
    }

 */
}
