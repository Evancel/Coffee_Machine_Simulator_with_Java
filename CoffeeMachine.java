package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int waterSupply;
    private int milkSupply;
    private int coffeeSupply;
    private int disposableCup;
    private int money;

    private Button currentChoice;

    private final Scanner sc;

    public CoffeeMachine(Scanner scanner, int waterSupply, int milkSupply, int coffeeSupply, int disposableCup, int money) {
        this.sc = scanner;
        this.waterSupply = waterSupply;
        this.milkSupply = milkSupply;
        this.coffeeSupply = coffeeSupply;
        this.disposableCup = disposableCup;
        this.money = money;
    }

    public void work() {
        do {
            userInput();
        } while (currentChoice != Button.EXIT);
    }

    private void userInput() {
        System.out.println("Write action (buy, fill, take, remaining, exit): ");

        if (!validateButtonSelection(sc.nextLine())) {
            System.out.println("Your input is wrong:\n" +
                    "If you want to buy a coffee type BUY,\n" +
                    "if you want to fill supplies type FILL,\n" +
                    "if you want to take money type TAKE,\n" +
                    "if you want to check all supplies type REMAINING,\n" +
                    "if you want to exit type EXIT.\n" +
                    "Please try again.");
            return;
        }

        proceedButton();
        System.out.println();
    }

    private boolean validateButtonSelection(String userInput) {
        boolean isInputValid = false;
        for (Button b : Button.values()) {
            if (userInput.equalsIgnoreCase(b.getDescription())) {
                isInputValid = true;
                currentChoice = b;
                break;
            }
        }
        return isInputValid;
    }

    private void printSuppliesRemaining() {
        System.out.printf("The coffee machine has:\n" +
                "%d ml of water\n" +
                "%d ml of milk\n" +
                "%d g of coffee beans\n" +
                "%d disposable cups\n" +
                "$%d of money\n"
                , waterSupply, milkSupply, coffeeSupply, disposableCup, money);
    }

    private void proceedButton() {
        System.out.println();
        switch (currentChoice) {
            case BUY -> buyCoffee();
            case FILL -> fillSupplies();
            case TAKE -> takeMoney();
            case STATUS -> printSuppliesRemaining();
        }
    }

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

    private int validateCoffeeChoice() {
        boolean isInputValid = false;
        int coffeeChoice = 0;
        do {
            System.out.println("What do you want to buy? \n" +
                    " 1 - espresso,\n" +
                    " 2 - latte,\n" +
                    " 3 - cappuccino,\n" +
                    " back - return to the main menu:");
            String userInput = sc.nextLine();

            if(userInput.equalsIgnoreCase("back")){
                return 0;
            }

            try {
                coffeeChoice = Integer.parseInt(userInput);
                isInputValid = (coffeeChoice > 0) && (coffeeChoice <= 3);
                if (!isInputValid) {
                    System.out.println("Your input is wrong. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Your input is wrong. Try again.");
            }
        } while (!isInputValid);

        return coffeeChoice;
    }

    private void makeCoffee(int coffeeChoice) throws IllegalArgumentException{

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
            System.out.println("I have enough resources, making you a coffee!");
            updateSupplies( - coffee.getWaterPerCup(),
                                - coffee.getMilkPerCup(),
                                - coffee.getCoffeePerCup(),
                                - coffee.getCup(),
                                coffee.getPricePerCup());
        }
    }

    private boolean hasEnoughResources (int water, int milk, int coffee, int cup) {
        boolean areResourcesEnough  = false;
        if (waterSupply < water) {
            System.out.println("Sorry, not enough water!");
            return false;
        }

        if (milkSupply < milk) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }

        if (coffeeSupply < coffee) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        }

        if (disposableCup < cup) {
            System.out.println("Sorry, not enough disposable cups!");
            return false;
        }

        areResourcesEnough  = true;

        return areResourcesEnough;
    }

    private void updateSupplies(int water, int milk, int coffee, int cup, int price) {
        waterSupply += water;
        milkSupply += milk;
        coffeeSupply += coffee;
        disposableCup += cup;
        money += price;
    }

    private void takeMoney() {
        System.out.printf("I gave you $%d\n", money);
        updateSupplies(0, 0, 0, 0, -money);
    }

    private void fillSupplies() {
        int fillWater = validateSupplyFilling("Write how many ml of water you want to add:");
        int fillMilk = validateSupplyFilling("Write how many ml of milk you want to add:");
        int fillCoffee = validateSupplyFilling("Write how many grams of coffee beans you want to add:");
        int fillCups = validateSupplyFilling("Write how many disposable cups you want to add:");
        int fillMoney = 0;

        updateSupplies(fillWater, fillMilk, fillCoffee, fillCups, fillMoney);
    }

    private int validateSupplyFilling(String menuPrompt) {
        boolean isInputValid = false;
        int fillSupply = 0;
        do {
            System.out.println(menuPrompt);

            try {
                fillSupply = Integer.parseInt(sc.nextLine());
                isInputValid = (fillSupply >= 0);

                if (!isInputValid) {
                    System.out.println("Your input is wrong.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Your input is wrong.");
            }
        } while (!isInputValid);

        return fillSupply;
    }
}
