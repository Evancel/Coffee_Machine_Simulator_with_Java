package coffeeMachine;

import java.util.Scanner;
/**
 * The CoffeeMachine class simulates a coffee machine that manages resources
 * such as water, milk, coffee, and disposable cups. It allows users to buy coffee,
 * refill supplies, take money, and check the machine's status.
 */
public class CoffeeMachine {
    private int waterSupply;
    private int milkSupply;
    private int coffeeSupply;
    private int disposableCup;
    private int money;

    private Button currentChoice;
    private final UserInterface ui;
    private final Scanner scanner;

    public CoffeeMachine() {
        this.scanner = new Scanner(System.in);
        this.ui = new UserInterface(scanner);

    }

    /**
     * Constructor that initializes the CoffeeMachine with specified resource levels.
     *
     * @param waterSupply    The initial amount of water in the machine.
     * @param milkSupply     The initial amount of milk in the machine.
     * @param coffeeSupply   The initial amount of coffee in the machine.
     * @param disposableCup  The initial number of disposable cups.
     * @param money          The initial amount of money in the machine.
     */
    public CoffeeMachine(int waterSupply, int milkSupply, int coffeeSupply, int disposableCup, int money) {
        this.scanner = new Scanner(System.in);
        this.ui = new UserInterface(scanner);
        this.waterSupply = waterSupply;
        this.milkSupply = milkSupply;
        this.coffeeSupply = coffeeSupply;
        this.disposableCup = disposableCup;
        this.money = money;
    }

    /**
     * Starts the coffee machine and allows the user to interact with it
     * until the 'exit' option is selected.
     */
    public void start() {
        do {
            work();
        } while (currentChoice != Button.EXIT);
        ui.exit();
    }

    /**
     * Validates the user's input choice.
     *
     * @return true if the user input is valid, false otherwise.
     */
    private boolean userInputIsValid() {
        currentChoice = ui.userChoice();
        return currentChoice != null;
    }

    /**
     * Performs actions based on the user's choice: buy coffee, fill supplies,
     * take money, or check status.
     */
    private void work() {
        if (!userInputIsValid()) {
            return;
        }

        switch (currentChoice) {
            case BUY -> buyCoffee();
            case FILL -> fillSupplies();
            case TAKE -> takeMoney();
            case STATUS -> statusInfo();
        }
    }

    /**
     * Handles the coffee purchase process by validating and making the coffee.
     */
    private void buyCoffee() {
        int coffeeChoice = ui.validateCoffeeChoice();
        if (coffeeChoice == 0) {
            return;
        }

        try {
            makeCoffee(coffeeChoice);
        } catch (IllegalArgumentException e) {
            ui.display(e.getMessage());
        }
    }

    /**
     * Makes a coffee based on the user's choice and updates the machine's resources.
     *
     * @param coffeeChoice The type of coffee selected by the user.
     * @throws IllegalArgumentException if the coffee type is invalid.
     */
    private void makeCoffee(int coffeeChoice) throws IllegalArgumentException {
        Beverage coffee = switch (coffeeChoice) {
            case 1 -> new Beverage(250, 0, 16, 4);
            case 2 -> new Beverage(350, 75, 20, 7);
            case 3 -> new Beverage(200, 100, 12, 6);
            default ->
                    throw new IllegalArgumentException("Internal error. Can't create the Beverage. Check user's choice.");
        };

        if (hasEnoughResources(coffee.getWaterPerCup(),
                coffee.getMilkPerCup(),
                coffee.getCoffeePerCup(),
                coffee.getCup())) {
            ui.displaySuccessMessage();
            updateSupplies(-coffee.getWaterPerCup(),
                    -coffee.getMilkPerCup(),
                    -coffee.getCoffeePerCup(),
                    -coffee.getCup(),
                    coffee.getPricePerCup());
        }
    }

    /**
     * Checks if the machine has enough resources to make the selected coffee.
     *
     * @param water  The amount of water required.
     * @param milk   The amount of milk required.
     * @param coffee The amount of coffee required.
     * @param cup    The number of disposable cups required.
     * @return true if there are enough resources, false otherwise.
     */
    private boolean hasEnoughResources(int water, int milk, int coffee, int cup) {
        if (waterSupply < water) {
            ui.displayNotEnoughWater();
            return false;
        }
        if (milkSupply < milk) {
            ui.displayNotEnoughMilk();
            return false;
        }
        if (coffeeSupply < coffee) {
            ui.displayNotEnoughCoffee();
            return false;
        }
        if (disposableCup < cup) {
            ui.displayNotEnoughCups();
            return false;
        }

        return true;
    }

    /**
     * Updates the machine's resources after making a coffee or refilling supplies.
     *
     * @param water  The change in water supply.
     * @param milk   The change in milk supply.
     * @param coffee The change in coffee supply.
     * @param cup    The change in disposable cups.
     * @param price  The amount of money gained from selling the coffee.
     */
    private void updateSupplies(int water, int milk, int coffee, int cup, int price) {
        waterSupply += water;
        milkSupply += milk;
        coffeeSupply += coffee;
        disposableCup += cup;
        money += price;
    }

    /**
     * Allows the user to take the money from the machine.
     */
    private void takeMoney() {
        ui.printMoneyMessage(money);
        updateSupplies(0, 0, 0, 0, -money);
    }

    /**
     * Refills the machine's resources based on user input.
     */
    private void fillSupplies() {
        int fillWater = ui.validateWater();
        int fillMilk = ui.validateMilk();
        int fillCoffee = ui.validateCoffee();
        int fillCups = ui.validateCups();
        int fillMoney = 0;

        updateSupplies(fillWater, fillMilk, fillCoffee, fillCups, fillMoney);
    }

    /**
     * Displays the current status of the machine's resources.
     */
    private void statusInfo() {
        ui.printSuppliesRemaining(waterSupply, milkSupply, coffeeSupply, disposableCup, money);
    }
}
