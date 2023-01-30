package services;

import errors.ErrorType;
import interfaces.ServiceManagement;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MessageService implements ServiceManagement {

    public void resourceMessageHandler(int index) {
//        System.out.println("You need more " + allExchangeResourceNames[index] + "!");
    }

    public void resourceMessageHandler(int index, int amount) {
//        System.out.println(amount + " " + allExchangeResourceNames[index] + " has been collected!");
    }

    public void marketExchangeMessageHandler(boolean buyOrSell, int index, int amount) {
        if (buyOrSell) {
            System.out.println(amount + " " + allExchangeResourceNames[index] + " has been bought!");
        } else {
            System.out.println(amount + " " + allExchangeResourceNames[index] + " has been sold!");
        }
    }

    public void marketBuySellResourceRatio(int index, int amount, int value) {
        System.out.println("Amount: " + amount + ", value: " + value);
        double ratio = value / amount;
        String stringBuilder = "Exchange ratio of " +
                allExchangeResourceNames[index] +
                " is: " +
                String.format("%1$" + 4 + ".15f", ratio);
        System.out.println(stringBuilder);
    }

    public void stockpileInfo(int[] currentValues) {
        System.out.println("Current stockpile values:");
        System.out.println("------------------------------------------------");
        for (int i = 0; i < currentValues.length; i++) {
            System.out.println(formatHandler(new StringBuilder(), allResourceNames[i], currentValues[i]));
        }
        System.out.println("------------------------------------------------");
    }

    public void stockExchangeInfo(double[] currentSellValues, double[] currentBuyValues) {
        System.out.println("Current stock exchange values:");
        System.out.println("---------------------------------");
        for (int i = 0; i < currentSellValues.length; i++) {
            System.out.print(formatHandler(new StringBuilder(), allExchangeResourceNames[i]));
        }
        System.out.println("\n-----------S--E--L--L------------");
        for (int i = 0; i < currentSellValues.length; i++) {
            System.out.print(formatHandler(new StringBuilder(), currentSellValues[i]));
        }
        System.out.println("\n------------B--U--Y--------------");
        for (int i = 0; i < currentSellValues.length; i++) {
            System.out.print(formatHandler(new StringBuilder(), currentBuyValues[i]));
        }
        System.out.println("\n---------------------------------");
    }

    public void exchangeFailureMessage(boolean buyOrSell, int index) {
        if (buyOrSell) {
            System.out.println("Not enough gold to buy resource!");
        } else {
            System.out.println("Not enough " + allExchangeResourceNames[index] + " to sell!");
        }
    }

    private StringBuilder formatHandler(StringBuilder stringBuilder, String resourceName) {
        stringBuilder.append(" | ").append(resourceName).append(" | ");
        return stringBuilder;
    }

    private StringBuilder formatHandler(StringBuilder stringBuilder, double currentValue) {
        stringBuilder.append(" | ").append(String.format("%1$"+4+ ".0f", currentValue)).append(" | ");
        return stringBuilder;
    }

    private StringBuilder formatHandler(StringBuilder stringBuilder, String resourceName, int currentValue) {
        stringBuilder.append(resourceName).append(" | ").append(currentValue).append(" | ");
        return stringBuilder;
    }
}
