package services;

import interfaces.ServiceManagement;
import resources.Stockpile;

import static java.lang.Math.round;

public class StockExchangeService implements ServiceManagement {

    private static double[] currentBuyValues = new double[]{ 130, 130, 169 };
    private static double[] currentSellValues = new double[]{ 70, 70, 91 };
    private static double[] currentValues = new double[]{ 100, 100, 130 };
    private Stockpile currentPlayerStockpile;
    private final MessageService messageService;
    private static double changeParameter;
    private double commodityFee;

    public StockExchangeService(Stockpile currentPlayerStockpile, MessageService messageService, double changeParameter, double commodityFee) {
        this.currentPlayerStockpile = currentPlayerStockpile;
        StockExchangeService.changeParameter = changeParameter;
        this.messageService = messageService;
        this.commodityFee = commodityFee;
    }

    public static double[] getCurrentValues() {
        return currentValues;
    }

    public static double getCurrentValue(int index) {
        return currentValues[index];
    }

    public static void setCurrentValues(double[] currentValues) {
        StockExchangeService.currentValues = currentValues;
    }

    public static void setCurrentValue(int index, double currentValue) {
        StockExchangeService.currentValues[index] = currentValue;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public double[] getCurrentBuyValues() {
        return currentBuyValues;
    }

    public static double getCurrentBuyValue(int index) {
        return currentBuyValues[index];
    }

    private void setCurrentBuyValues(double[] currentBuyValues) {
        StockExchangeService.currentBuyValues = currentBuyValues;
    }

    private void setCurrentBuyValue(int index, double currentBuyValue) {
        StockExchangeService.currentBuyValues[index] = currentBuyValue;
    }

    public double[] getCurrentSellValues() {
        return currentSellValues;
    }

    public double getCurrentSellValue(int index) {
        return currentSellValues[index];
    }

    private void setCurrentSellValues(double[] currentSellValues) {
        StockExchangeService.currentSellValues = currentSellValues;
    }

    private void setCurrentSellValue(int index, double currentSellValue) {
        StockExchangeService.currentSellValues[index] = currentSellValue;
    }

    private Stockpile getCurrentPlayerStockpile() {
        return currentPlayerStockpile;
    }

    public void setCurrentPlayerStockpile(Stockpile currentPlayerStockpile) {
        this.currentPlayerStockpile = currentPlayerStockpile;
    }

    public double getChangeParameter() {
        return changeParameter;
    }

    public void setChangeParameter(double changeParameter) {
        StockExchangeService.changeParameter = changeParameter;
    }

    public double getCommodityFee() {
        return commodityFee;
    }

    public void setCommodityFee(double commodityFee) {
        this.commodityFee = commodityFee;
    }

    public boolean exchangeResources(boolean buyOrSell, int index, int amount, Stockpile playerStockpile) {
        if (buyOrSell && buyValidation(index, amount, playerStockpile)) {
            playerStockpile.useResource(2, computeCurrentValuesAfterBuy(index, amount));
            playerStockpile.collectResource(index == 2 ? 3 : index, amount);
        } else if (!buyOrSell && sellValidation(index, amount, playerStockpile)) {
            playerStockpile.useResource(index == 2 ? 3 : index, amount);
            playerStockpile.collectResource(2, computeCurrentValuesAfterSell(index, amount));
        } else {
            return failedToExchange(buyOrSell, index);
        }
        return true;
    }

    private static boolean resourceAmountValidation(int resourceStockpile, int resourceAmount) {
        return resourceStockpile >= resourceAmount;
    }

    private boolean buyValidation(int index, int amount, Stockpile playerStockpile) {
        return resourceAmountValidation(playerStockpile.getResource(2), computeCurrentValuesAfterBuy(index, amount));
    }

    private boolean sellValidation(int index, int amount, Stockpile playerStockpile) {
        return resourceAmountValidation(playerStockpile.getResource(index == 2 ? 3 : index), amount);
    }

    private int computeCurrentValuesAfterBuy(int index, int amount) {
        int goldValue = 0;
        int ratio = amount / 100;
        for (int i = 0; i < ratio; i++) {
            goldValue += computeGoldBuyAmount(index);
            if (getCurrentValue(index) <= 9998) {
                setCurrentValue(index, getCurrentValue(index) + getChangeParameter());
                updateBuySellValues(index);
            }
        }
        System.out.println("Gold value is " + goldValue);
        messageService.marketBuySellResourceRatio(index, amount, goldValue);
        return goldValue;
    }

    private int computeCurrentValuesAfterSell(int index, int amount) {
        int goldValue = 0;
        int ratio = amount / 100;
        for (int i = 0; i < ratio; i++) {
            goldValue += computeGoldSellAmount(index);
            if (getCurrentValue(index) > 20) {
                setCurrentValue(index, getCurrentValue(index) - getChangeParameter());
                updateBuySellValues(index);
            }
        }
        System.out.println("Gold value is " + goldValue);
        messageService.marketBuySellResourceRatio(index, amount, goldValue);
        return goldValue;
    }

    private void updateBuySellValues(int index) {
        setCurrentBuyValue(index, getCurrentValue(index) * (1 + getCommodityFee()));
        setCurrentSellValue(index, getCurrentValue(index) * (1 - getCommodityFee()));
    }

    private int computeGoldBuyAmount(int index) {
        return (int) round(getCurrentValue(index) * (1 + getCommodityFee()));
    }

    private int computeGoldSellAmount(int index) {
        return (int) round(getCurrentValue(index) * (1 - getCommodityFee()));
    }

    private boolean failedToExchange(boolean buyOrSell, int index) {
        getMessageService().exchangeFailureMessage(buyOrSell, index);
        return false;
    }
}
