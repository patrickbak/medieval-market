import buildings.Market;
import resources.Stockpile;
import services.MessageService;
import services.StockExchangeService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
//        float[] currentValueMultipliers = new float[]{ 1.0f, 1.0f, 1.0f, 1.0f };
        int[] currentValues = new int[]{ 1000000000, 1000000000, 0, 1000000000 };
        MessageService messageService = new MessageService();
        Stockpile player1Stockpile = new Stockpile(messageService, currentValues);
        StockExchangeService stockExchangeService = new StockExchangeService(player1Stockpile, messageService, 3, 0.3f);
        int amount = 100;
        Market player1Market = new Market(messageService, player1Stockpile, stockExchangeService);
        player1Market.getExchangeInfo();
        player1Market.sell(1, 2137 * amount);
        System.out.println(Arrays.toString(player1Stockpile.getAllResources()));
    }
}