package coffeeMachine;
/**
 * The Main class serves as the entry point of the coffee machine simulation program.
 * It initializes the coffee machine with default or custom supply values and starts
 * the user interaction by invoking the coffee machine's functionality.
 */
public class Main {
    /**
     * The main method initializes a CoffeeMachine instance
     * with default supply values and then invokes the 'work' method on the CoffeeMachine
     * to start the coffee machine's interaction loop.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        int initialWaterSupply = 400;
        int initialMilkSupply = 540;
        int initialCoffeeSupply = 120;
        int initialDisposableCups = 9;
        int initialMoney = 550;
        CoffeeMachine coffeeMachine = new CoffeeMachine( initialWaterSupply, initialMilkSupply, initialCoffeeSupply, initialDisposableCups, initialMoney);
        coffeeMachine.start();
    }
}
