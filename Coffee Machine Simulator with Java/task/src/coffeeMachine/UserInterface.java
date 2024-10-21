package coffeeMachine;

import java.util.Scanner;

/**
 * The UserInterface class handles the interaction between the user and the CoffeeMachine.
 * It collects user input, displays messages, and validates the input.
 */
public class UserInterface {

    private final Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prompts the user to choose an action from the menu and validates the input.
     *
     * @return A {@link Button} representing the user's choice, or null if the input is invalid.
     */
    public Button userChoice() {
        String input = printMenu();

        if (!validateButtonSelection(input)) {
            wrongChoice();
            return null;
        }
        return setButton(input);
    }

    /**
     * Displays the remaining supplies in the CoffeeMachine.
     *
     * @param waterSupply   The amount of water remaining.
     * @param milkSupply    The amount of milk remaining.
     * @param coffeeSupply  The amount of coffee remaining.
     * @param disposableCup The number of disposable cups remaining.
     * @param money         The amount of money in the CoffeeMachine.
     */
    public void printSuppliesRemaining(int waterSupply, int milkSupply, int coffeeSupply,
                                       int disposableCup, int money) {
        String remainingInfo = String.format(Messages.STATUS_INFO_FORMAT, waterSupply, milkSupply,
                coffeeSupply, disposableCup, money);
        display(remainingInfo);
    }

    /**
     * Validates the user's coffee selection and returns the choice.
     *
     * @return The coffee choice (1, 2, or 3) or 0 if the user chooses to go back.
     */
    public int validateCoffeeChoice() {
        boolean isInputValid = false;
        int coffeeChoice = 0;
        do {
            String userInput = printBeverages();

            if (userInput.equalsIgnoreCase("back")) {
                return 0;
            }

            try {
                coffeeChoice = Integer.parseInt(userInput);
                isInputValid = (coffeeChoice > 0) && (coffeeChoice <= 3);
                if (!isInputValid) {
                    wrongInput();
                }
            } catch (NumberFormatException e) {
                wrongInput();
            }
        } while (!isInputValid);

        return coffeeChoice;
    }

    /**
     * Displays a success message when there are enough resources to make coffee.
     */
    public void displaySuccessMessage() {
        display(Messages.ENOUGH_RESOURCES);
    }

    /**
     * Displays a message indicating there is not enough water to make coffee.
     */
    public void displayNotEnoughWater() {
        display(Messages.NOT_ENOUGH_WATER);
    }

    /**
     * Displays a message indicating there is not enough milk to make coffee.
     */
    public void displayNotEnoughMilk() {
        display(Messages.NOT_ENOUGH_MILK);
    }

    /**
     * Displays a message indicating there is not enough coffee to make coffee.
     */
    public void displayNotEnoughCoffee() {
        display(Messages.NOT_ENOUGH_COFFEE);
    }

    /**
     * Displays a message indicating there are not enough disposable cups.
     */
    public void displayNotEnoughCups() {
        display(Messages.NOT_ENOUGH_CUPS);
    }

    /**
     * Prompts the user to add water to the CoffeeMachine.
     *
     * @return The amount of water to add.
     */
    public int validateWater() {
        return validateSupplyFilling(Messages.ADD_WATER_PROMPT);
    }

    /**
     * Prompts the user to add milk to the CoffeeMachine.
     *
     * @return The amount of milk to add.
     */
    public int validateMilk() {
        return validateSupplyFilling(Messages.ADD_MILK_PROMPT);
    }

    /**
     * Prompts the user to add coffee to the CoffeeMachine.
     *
     * @return The amount of coffee to add.
     */
    public int validateCoffee() {
        return validateSupplyFilling(Messages.ADD_COFFEE_PROMPT);
    }

    /**
     * Prompts the user to add disposable cups to the CoffeeMachine.
     *
     * @return The number of disposable cups to add.
     */
    public int validateCups() {
        return validateSupplyFilling(Messages.ADD_CUPS_PROMPT);
    }

    /**
     * Displays the amount of money taken from the CoffeeMachine.
     *
     * @param money The amount of money taken.
     */
    public void printMoneyMessage(int money) {
        String takeMoney = String.format(Messages.TAKE_MONEY_MESSAGE, money);
        display(takeMoney);
    }

    /**
     * Displays a message indicating the user made an invalid choice.
     */
    public void wrongChoice() {
        display(Messages.WRONG_CHOICE);
    }

    /**
     * Displays a message indicating the user entered invalid input.
     */
    public void wrongInput() {
        display(Messages.WRONG_INPUT);
    }

    /**
     * Displays a given message to the user.
     *
     * @param message The message to display.
     */
    public void display(String message) {
        System.out.println(message);
    }

    /**
     * Closes the scanner used for user input when exiting the CoffeeMachine.
     */
    public void exit() {
        scanner.close();
    }

    /**
     * Prints the menu options and returns the user's input.
     *
     * @return The user's input as a string.
     */
    private String printMenu() {
        return getUserInput(Messages.MENU);
    }

    /**
     * Validates if the user input corresponds to a valid button selection.
     *
     * @param userInput The user's input.
     * @return true if the input is valid, false otherwise.
     */
    private boolean validateButtonSelection(String userInput) {
        for (Button button : Button.values()) {
            if (userInput.equalsIgnoreCase(button.getDescription())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the button corresponding to the user's input.
     *
     * @param userInput The user's input.
     * @return The {@link Button} corresponding to the input, or null if the input is invalid.
     */
    private Button setButton(String userInput) {
        for (Button button : Button.values()) {
            if (userInput.equalsIgnoreCase(button.getDescription())) {
                return button;
            }
        }
        return null;
    }

    /**
     * Prints the coffee menu and returns the user's input.
     *
     * @return The user's input as a string.
     */
    private String printBeverages() {
        return getUserInput(Messages.BEVERAGE_MENU);
    }

    /**
     * Validates the user's input when adding supplies to the CoffeeMachine.
     *
     * @param menuPrompt The prompt displayed to the user.
     * @return The amount of supplies to add.
     */
    private int validateSupplyFilling(String menuPrompt) {
        boolean isInputValid = false;
        int fillSupply = 0;
        do {
            String input = getUserInput(menuPrompt);

            try {
                fillSupply = Integer.parseInt(input);
                isInputValid = (fillSupply >= 0);

                if (!isInputValid) {
                    display(Messages.WRONG_INPUT);
                }
            } catch (NumberFormatException e) {
                display(Messages.WRONG_INPUT);
            }
        } while (!isInputValid);

        return fillSupply;
    }


    /**
     * Prompts the user for input and returns the entered value.
     *
     * @param prompt The prompt to display.
     * @return The user's input as a string.
     */
    private String getUserInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}
