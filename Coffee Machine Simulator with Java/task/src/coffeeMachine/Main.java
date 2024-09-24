package coffeeMachine;

import java.util.Scanner;
/**
 * The Main class serves as the entry point of the coffee machine simulation program.
 * It initializes the coffee machine with default or custom supply values and starts
 * the user interaction by invoking the coffee machine's functionality.
 *
 * This class contains the main method that is executed when the program is run.
 * It sets up necessary resources, like a Scanner object for user input, and
 * passes it to the CoffeeMachine instance.
 *
 * This class is not meant to contain business logic; its main responsibility
 * is to initialize the system and hand over control to other classes like CoffeeMachine.
 */
public class Main {
    static Scanner scanner = new Scanner(System.in);

    /**
     * The main method, which is the starting point of the program.
     * It creates a Scanner for user input and initializes a CoffeeMachine instance
     * with default supply values. It then invokes the 'work' method on the CoffeeMachine
     * to start the coffee machine's interaction loop.
     *
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        CoffeeMachine cm = new CoffeeMachine(scanner, 400,540,120,9,550);
        cm.work();
        scanner.close();
    }
}
