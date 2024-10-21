package coffeeMachine;

/**
 * The Main class initializes the coffee machine with default or custom supply values and starts
 * the user interaction by invoking the coffee machine's functionality.
 */
public class Main {
    /**
     * Default initial supply values for the coffee machine.
     */
    static int initialWaterSupply = 400;
    static int initialMilkSupply = 540;
    static int initialCoffeeSupply = 120;
    static int initialDisposableCups = 9;
    static int initialMoney = 550;

    /**
     * The main method initializes a CoffeeMachine instance
     * with default supply values and then invokes the 'start' method on the CoffeeMachine
     * to start the coffee machine's interaction loop.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(initialWaterSupply, initialMilkSupply,
                initialCoffeeSupply, initialDisposableCups, initialMoney);
        coffeeMachine.start();
    }
}
