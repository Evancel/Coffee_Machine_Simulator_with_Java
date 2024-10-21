package coffeeMachine;

/**
 * The Beverage class represents a beverage in a system, f.e. espresso, cappuccino, latte.
 * It holds details like waterPerCup, milkPerCup, coffeePerCup, pricePerCup, cup.
 * It provides methods to get the amount of each supply per cup.
 */

public class Beverage {
    private final int waterPerCup;
    private final int milkPerCup;
    private final int coffeePerCup;
    private final int pricePerCup;
    private final int cup;

    /**
     * Constructs a new Beverage with the given amount of water, milk, coffee and price.
     *
     * @param water describes water amount per cup
     * @param milk describes milk amount per cup
     * @param coffee describes coffee amount per cup
     * @param price describes price for a cup
     */

    public Beverage(int water, int milk, int coffee, int price){
        this.waterPerCup = water;
        this.milkPerCup = milk;
        this.coffeePerCup = coffee;
        this.cup = 1;
        this.pricePerCup = price;
    }

    int getWaterPerCup() {
        return waterPerCup;
    }
    int getMilkPerCup() {
        return milkPerCup;
    }
    int getCoffeePerCup() {
        return coffeePerCup;
    }
    int getPricePerCup() {
        return pricePerCup;
    }
    int getCup() {
        return cup;
    }
}
