package machine;

public class Beverage {
    private final int waterPerCup;
    private final int milkPerCup;
    private final int coffeePerCup;
    private final int pricePerCup;
    private final int cup;

    public Beverage(int water, int milk, int coffee, int cup, int price){
        this.waterPerCup = water;
        this.milkPerCup = milk;
        this.coffeePerCup = coffee;
        this.cup = cup;
        this.pricePerCup = price;
    }

    public int getWaterPerCup() {
        return waterPerCup;
    }

    public int getMilkPerCup() {
        return milkPerCup;
    }

    public int getCoffeePerCup() {
        return coffeePerCup;
    }

    public int getPricePerCup() {
        return pricePerCup;
    }

    public int getCup() {
        return cup;
    }
}
