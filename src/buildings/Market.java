package buildings;

import resources.Stockpile;
import services.MessageService;
import services.StockExchangeService;

public class Market {

    private static StockExchangeService stockExchangeService;
    private final MessageService messageService;
    private Stockpile playerStockpile;

    public Market(MessageService messageService, Stockpile playerStockpile, StockExchangeService stockExchangeService) {
        this.messageService = messageService;
        this.playerStockpile = playerStockpile;
        Market.stockExchangeService = stockExchangeService;
    }

    public Stockpile getPlayerStockpile() {
        return playerStockpile;
    }

    public void setPlayerStockpile(Stockpile playerStockpile) {
        this.playerStockpile = playerStockpile;
    }

    public void sell(int index, int amount) {
        getExchangeInfo(false, index, amount, stockExchangeService.exchangeResources(false, index, amount, getPlayerStockpile()));
    }

    public void buy(int index,  int amount) {
        getExchangeInfo(true, index, amount, stockExchangeService.exchangeResources(true, index, amount, getPlayerStockpile()));
    }

    public void getExchangeInfo(boolean buyOrSell, int index,  int amount, boolean isValid) {
        if (isValid) {
            messageService.marketExchangeMessageHandler(buyOrSell, index, amount);
        }
        getExchangeInfo();
    }

    public void getExchangeInfo() {
        messageService.stockExchangeInfo(stockExchangeService.getCurrentSellValues(), stockExchangeService.getCurrentBuyValues());
    }
}
