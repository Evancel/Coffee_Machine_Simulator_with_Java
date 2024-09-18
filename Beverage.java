package machine;

public class Beverage {
    private final int waterPerCup;
    private final int milkPerCup;
    private final int coffeePerCup;
    private final int pricePerCup;
    private final int cup;

    public Beverage(int water, int milk, int coffee, int price){
        this.waterPerCup = water;
        this.milkPerCup = milk;
        this.coffeePerCup = coffee;
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

    public int getCup() {
        return cup;
    }
}
