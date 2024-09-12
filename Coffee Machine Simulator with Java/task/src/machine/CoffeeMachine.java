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
        String input = sc.nextLine();

        if (!validationUserInputToTheExistingButtons(input)) {
            System.out.println("""
                    Your input is wrong:
                    If you want to buy a coffee type BUY,
                    if you want to fill supplies type FILL,
                    if you want to take money type TAKE,
                    if you want to check all supplies type REMAINING,
                    if you want to exit type EXIT.
                    Please try again.""");
            System.out.println();
            return;
        }

        proceedButton();
        System.out.println();
    }

    private boolean validationUserInputToTheExistingButtons(String someInput) {
        boolean isValidInput = false;
        for (Button b : Button.values()) {
            if (someInput.equalsIgnoreCase(b.getDescription())) {
                isValidInput = true;
                currentChoice = b;
                break;
            }
        }
        return isValidInput;
    }

    private void printSuppliesAmount() {
        System.out.printf("""
                The coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money
                """, waterSupply, milkSupply, coffeeSupply, disposableCup, money);
    }

    private void proceedButton() {
        switch (currentChoice) {
            case BUY -> buyCoffee();
            case FILL -> inputSupplyQuantities();
            case TAKE -> takeMoney();
            case STATUS -> printSuppliesAmount();
        }
    }

    private void buyCoffee() {
        int typeCoffee = validationUserInputToTheExistingTypeCoffee(
                "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - return to the main menu:");
        if(typeCoffee == 0){
            return;
        }

        makeCoffee(typeCoffee);
    }

    private int validationUserInputToTheExistingTypeCoffee(String s) {
        boolean isInputSuccess = false;
        int typeCoffee = 0;
        do {
            System.out.println(s);
            String input = sc.nextLine();

            if(input.equalsIgnoreCase("back")){
                return 0;
            }

            try {
                typeCoffee = Integer.parseInt(input);
                isInputSuccess = (typeCoffee > 0) && (typeCoffee <= 3);
                if (!isInputSuccess) {
                    System.out.println("Your input is wrong. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Your input is wrong. Try again.");
            }
        } while (!isInputSuccess);

        return typeCoffee;
    }

    private void makeCoffee(int typeCoffee) {

        int waterPerCup = 0;
        int milkPerCup = 0;
        int coffeePerCup = 0;
        int pricePerCup = 0;
        int cup = 1;

        switch (typeCoffee) {
            case 1:
                waterPerCup = 250;
                coffeePerCup = 16;
                pricePerCup = 4;
                break;
            case 2:
                waterPerCup = 350;
                milkPerCup = 75;
                coffeePerCup = 20;
                pricePerCup = 7;
                break;
            case 3:
                waterPerCup = 200;
                milkPerCup = 100;
                coffeePerCup = 12;
                pricePerCup = 6;
                break;
            default:
                System.out.println("Internal Error. Unknown type of coffee!");
                break;
        }

        if (isEnoughResourcesForTheCup(waterPerCup, milkPerCup, coffeePerCup, cup)) {
            System.out.println("I have enough resources, making you a coffee!");
            updateSuppliesAmount(-waterPerCup, -milkPerCup, -coffeePerCup, -cup, pricePerCup);
        }
    }

    private boolean isEnoughResourcesForTheCup(int water, int milk, int coffee, int cup) {
        boolean isEnoughResources = false;
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

        isEnoughResources = true;

        return isEnoughResources;
    }

    private void updateSuppliesAmount(int water, int milk, int coffee, int cup, int price) {
        waterSupply += water;
        milkSupply += milk;
        coffeeSupply += coffee;
        disposableCup += cup;
        money += price;
    }

    private void takeMoney() {
        System.out.printf("I gave you $%d\n", money);
        updateSuppliesAmount(0, 0, 0, 0, -money);
    }

    private void inputSupplyQuantities() {
        int addWater = validationAddSupply("Write how many ml of water you want to add:");
        int addMilk = validationAddSupply("Write how many ml of milk you want to add:");
        int addCoffee = validationAddSupply("Write how many grams of coffee beans you want to add:");
        int addCups = validationAddSupply("Write how many disposable cups you want to add:");
        int addMoney = 0;

        updateSuppliesAmount(addWater, addMilk, addCoffee, addCups, addMoney);
    }

    private int validationAddSupply(String s) {
        boolean isInputSuccess = false;
        int addSupply = 0;
        do {
            System.out.println(s);
            String input = sc.nextLine();
            try {
                addSupply = Integer.parseInt(input);
                isInputSuccess = (addSupply >= 0);
                if (!isInputSuccess) {
                    System.out.println("Your input is wrong.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Your input is wrong.");
            }
        } while (!isInputSuccess);

        return addSupply;
    }
}
