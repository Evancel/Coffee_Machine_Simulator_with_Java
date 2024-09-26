package coffeeMachine;

import java.util.Scanner;
/**
 * The CoffeeMachine class simulates a simple coffee machine.
 * It manages the supplies of water, milk, coffee beans, disposable cups, and money.
 * Users can interact with the coffee machine by selecting various actions, such as buying coffee,
 * filling supplies, taking money, or viewing the remaining supplies.
 *
 * This class provides methods to validate user input, handle coffee purchases, refill supplies,
 * and ensure that there are enough resources available for making coffee.
 *
 * The user interacts with the coffee machine through a command-line interface (CLI), where they
 * input actions like "buy", "fill", "take", "remaining", or "exit".
 *
 * Attributes:
 * - waterSupply: the amount of water in the machine (in ml)
 * - milkSupply: the amount of milk in the machine (in ml)
 * - coffeeSupply: the amount of coffee beans in the machine (in grams)
 * - disposableCup: the number of disposable cups available
 * - money: the amount of money collected from coffee sales
 * - currentChoice: the user's current selected action (buy, fill, take, etc.)
 * - sc: a Scanner object for handling user input
 */
public class CoffeeMachine {
    private int waterSupply;
    private int milkSupply;
    private int coffeeSupply;
    private int disposableCup;
    private int money;

    private Button currentChoice;

    private final Scanner sc;

    // Define static constants
    private final static String MENU = "Write action (buy, fill, take, remaining, exit): ";
    private final static String WRONG_CHOICE = """
            Your input is wrong:
            to buy a coffee type BUY,
            to fill supplies type FILL,
            to take money type TAKE,
            to check all supplies type REMAINING,
            to exit type EXIT.
            
            Please try again.""";
    private final static String WRONG_INPUT = "Your input is wrong. Try again.";

    /**
     * Constructor for CoffeeMachine, initializing with default supplies.
     */
    public CoffeeMachine(){
        this.sc = new Scanner(System.in);
    }

    /**
     * Constructor for CoffeeMachine, initializing with custom supplies.
     *
     * @param waterSupply initial water supply in ml
     * @param milkSupply initial milk supply in ml
     * @param coffeeSupply initial coffee supply in grams
     * @param disposableCup initial count of disposable cups
     * @param money initial amount of money in dollars
     */
    public CoffeeMachine(int waterSupply, int milkSupply, int coffeeSupply, int disposableCup, int money) {
        this.sc = new Scanner(System.in);
        this.waterSupply = waterSupply;
        this.milkSupply = milkSupply;
        this.coffeeSupply = coffeeSupply;
        this.disposableCup = disposableCup;
        this.money = money;
    }

    /**
     * Starts the coffee machine's main loop, processing user input until "exit" is selected.
     */
    public void work() {
        do {
            userInput();
        } while (currentChoice != Button.EXIT);
        sc.close();
    }

    /**
     * Handles the user's input and validates it against available actions.
     */
    private void userInput() {
        System.out.println(MENU);

        if (!validateButtonSelection(sc.nextLine())) {
            System.out.println(WRONG_CHOICE);
            return;
        }

        proceedButton();
        System.out.println();
    }

    /**
     * Validates the user's button selection by comparing the input with the defined buttons.
     *
     * @param userInput the string entered by the user
     * @return true if the input matches a valid button action, false otherwise
     */
    private boolean validateButtonSelection(String userInput) {
        for (Button button : Button.values()) {
            if (userInput.equalsIgnoreCase(button.getDescription())) {
                currentChoice = button;
                return true;
            }
        }
        return false;
    }

    /**
     * Prints the current supplies of the coffee machine (water, milk, coffee, cups, and money).
     */
    private void printSuppliesRemaining() {
        String REMAINING_INFO = String.format("""
            The coffee machine has:
            %d ml of water
            %d ml of milk
            %d g of coffee beans
            %d disposable cups
            $%d of money""", waterSupply, milkSupply, coffeeSupply, disposableCup, money);
        System.out.println(REMAINING_INFO);
    }

    /**
     * Proceeds with the action based on the user's current choice.
     */
    private void proceedButton() {
        System.out.println();
        switch (currentChoice) {
            case BUY -> buyCoffee();
            case FILL -> fillSupplies();
            case TAKE -> takeMoney();
            case STATUS -> printSuppliesRemaining();
        }
    }

    /**
     * Handles the process of buying coffee. The user can choose from espresso, latte, or cappuccino.
     * Validates if the machine has enough resources to make the selected coffee.
     */
    private void buyCoffee() {
        int coffeeChoice  = validateCoffeeChoice();
        if(coffeeChoice  == 0){
            return;
        }

        try {
            makeCoffee(coffeeChoice);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Validates the user's coffee choice and returns the selection.
     *
     * @return the user's coffee choice, or 0 to return to the main menu
     */
    private int validateCoffeeChoice() {
        String BEVERAGE_MENU = """
            What do you want to buy?\s
             1 - espresso,
             2 - latte,
             3 - cappuccino,
             back - return to the main menu:""";

        boolean isInputValid = false;
        int coffeeChoice = 0;
        do {
            System.out.println(BEVERAGE_MENU);
            String userInput = sc.nextLine();

            if(userInput.equalsIgnoreCase("back")){
                return 0;
            }

            try {
                coffeeChoice = Integer.parseInt(userInput);
                isInputValid = (coffeeChoice > 0) && (coffeeChoice <= 3);
                if (!isInputValid) {
                    System.out.println(WRONG_INPUT);
                }
            } catch (NumberFormatException e) {
                System.out.println(WRONG_INPUT);
            }
        } while (!isInputValid);

        return coffeeChoice;
    }

    /**
     * Makes the selected coffee by checking if there are enough resources and updating supplies.
     *
     * @param coffeeChoice the type of coffee selected by the user
     * @throws IllegalArgumentException if the coffee choice is invalid
     */
    private void makeCoffee(int coffeeChoice) throws IllegalArgumentException{
        String ENOUGH_RESOURCES = "I have enough resources, making you a coffee!";

        Beverage coffee = switch (coffeeChoice) {
            case 1 -> new Beverage(250,0,16,4);
            case 2 -> new Beverage(350,75,20,7);
            case 3 -> new Beverage(200,100,12,6);
            default -> throw new IllegalArgumentException("Internal error. Can't create the Beverage. Check user's choice.");
        };

        if (hasEnoughResources (coffee.getWaterPerCup(),
                                    coffee.getMilkPerCup(),
                                    coffee.getCoffeePerCup(),
                                    coffee.getCup())) {
            System.out.println(ENOUGH_RESOURCES);
            updateSupplies( - coffee.getWaterPerCup(),
                                - coffee.getMilkPerCup(),
                                - coffee.getCoffeePerCup(),
                                - coffee.getCup(),
                                coffee.getPricePerCup());
        }
    }

    /**
     * Checks if the machine has enough resources to make the selected coffee.
     *
     * @param water the required amount of water
     * @param milk the required amount of milk
     * @param coffee the required amount of coffee beans
     * @param cup the required number of disposable cups
     * @return true if enough resources are available, false otherwise
     */
    private boolean hasEnoughResources (int water, int milk, int coffee, int cup) {
        String NOT_ENOUGH_WATER = "Sorry, not enough water!";
        String NOT_ENOUGH_MILK = "Sorry, not enough milk!";
        String NOT_ENOUGH_COFFEE = "Sorry, not enough coffee beans!";
        String NOT_ENOUGH_CUPS = "Sorry, not enough disposable cups!";

        if (waterSupply < water) {
            System.out.println(NOT_ENOUGH_WATER);
            return false;
        }

        if (milkSupply < milk) {
            System.out.println(NOT_ENOUGH_MILK);
            return false;
        }

        if (coffeeSupply < coffee) {
            System.out.println(NOT_ENOUGH_COFFEE);
            return false;
        }

        if (disposableCup < cup) {
            System.out.println(NOT_ENOUGH_CUPS);
            return false;
        }

        return true;
    }

    /**
     * Updates the machine's supplies after making coffee or other actions.
     *
     * @param water the amount of water used or added
     * @param milk the amount of milk used or added
     * @param coffee the amount of coffee beans used or added
     * @param cup the number of disposable cups used or added
     * @param price the amount of money earned from selling coffee
     */
    private void updateSupplies(int water, int milk, int coffee, int cup, int price) {
        waterSupply += water;
        milkSupply += milk;
        coffeeSupply += coffee;
        disposableCup += cup;
        money += price;
    }

    /**
     * Takes all the money currently in the machine and resets the money amount to zero.
     */
    private void takeMoney() {
        System.out.printf("I gave you $%d\n", money);
        updateSupplies(0, 0, 0, 0, -money);
    }

    /**
     * Prompts the user to input the amount of each supply they wish to add to the coffee machine.
     * The user is asked to input values for water (ml), milk (ml), coffee beans (grams),
     * and disposable cups (count).
     * Each input is validated using the `validateSupplyFilling` method to ensure non-negative
     * values are entered. After receiving valid inputs, the machine's supplies are updated accordingly.
     */
    private void fillSupplies() {
        String ADD_WATER_PROMPT = "Write how many ml of water you want to add:";
        String ADD_MILK_PROMPT = "Write how many ml of milk you want to add:";
        String ADD_COFFEE_PROMPT = "Write how many grams of coffee beans you want to add:";
        String ADD_CUPS_PROMPT = "Write how many disposable cups you want to add:";

        int fillWater = validateSupplyFilling(ADD_WATER_PROMPT);
        int fillMilk = validateSupplyFilling(ADD_MILK_PROMPT);
        int fillCoffee = validateSupplyFilling(ADD_COFFEE_PROMPT);
        int fillCups = validateSupplyFilling(ADD_CUPS_PROMPT);
        int fillMoney = 0;

        updateSupplies(fillWater, fillMilk, fillCoffee, fillCups, fillMoney);
    }

    /**
     * Validates the user's input for refilling supplies.
     * Displays the given menu prompt and reads the user's input. The input is then checked
     * to ensure it is a valid non-negative integer. If the input is invalid (either not a number
     * or a negative number), the user is prompted to try again. The method keeps prompting until
     * a valid input is received.
     *
     * @param menuPrompt the prompt message to display to the user for input
     * @return the validated supply amount as a non-negative integer
     */
    private int validateSupplyFilling(String menuPrompt) {
        boolean isInputValid = false;
        int fillSupply = 0;
        do {
            System.out.println(menuPrompt);

            try {
                fillSupply = Integer.parseInt(sc.nextLine());
                isInputValid = (fillSupply >= 0);

                if (!isInputValid) {
                    System.out.println(WRONG_INPUT);
                }
            } catch (NumberFormatException e) {
                System.out.println(WRONG_INPUT);
            }
        } while (!isInputValid);

        return fillSupply;
    }
}
