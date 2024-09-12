package machine;

public class Machine {
    enum Status {
        WAITING_INPUT,
        CHOOSING_A_COFFEE,
        ADDING_WATER,
        ADDING_MILK,
        ADDING_COFFEE,
        ADDING_CUPS,
        TAKING_MONEY,
        OUTPUT_CURR_STATUS,
        EXITING
    }

    private Status currentStatus;

    private int waterSupply;
    private int milkSupply;
    private int coffeeSupply;
    private int disposableCup;
    private int money;

    private int addWater;
    private int addMilk;
    private int addCoffee;
    private int addCup;

    public Machine(int water, int milk, int coffee, int cup, int money) {
        this.waterSupply = water;
        this.milkSupply = milk;
        this.coffeeSupply = coffee;
        this.disposableCup = cup;
        this.money = money;

        setWaitingInputStatus();
    }

    public void printCurrStatus() {
        System.out.println("Current status = " + currentStatus);
    }

    public boolean isWorking() {
        return currentStatus != Status.EXITING;
    }

    public void processingUserInput(String s) {
        switch (currentStatus) {
            case WAITING_INPUT:
                chooseActionFromUser(s);
                break;
            case CHOOSING_A_COFFEE:
                int chosenCoffee = validationUserInputToTheExistingTypeCoffee(s);
                if (chosenCoffee == -1) {
                    return;
                }else if(chosenCoffee == 0){
                    setWaitingInputStatus();
                    return;
                }
                makeCoffee(chosenCoffee);
                setWaitingInputStatus();
                break;
            case ADDING_WATER:
                addWater = validationAddSupply(s);
                if (addWater < 0) {
                    addWater = 0;
                    return;
                }
                System.out.println("Write how many ml of milk you want to add: ");
                currentStatus = Status.ADDING_MILK;
                break;
            case ADDING_MILK:
                addMilk = validationAddSupply(s);
                if (addMilk < 0) {
                    addMilk = 0;
                    return;
                }
                System.out.println("Write how many grams of coffee beans you want to add: ");
                currentStatus = Status.ADDING_COFFEE;
                break;
            case ADDING_COFFEE:
                addCoffee = validationAddSupply(s);
                if (addCoffee < 0) {
                    addCoffee = 0;
                    return;
                }
                System.out.println("Write how many disposable cups you want to add: ");
                currentStatus = Status.ADDING_CUPS;
                break;
            case ADDING_CUPS:
                addCup = validationAddSupply(s);
                if (addCup < 0) {
                    addCup = 0;
                    return;
                }
                updateSuppliesAmount(addWater, addMilk, addCoffee, addCup, 0);
                clearAddingSupplies();
                setWaitingInputStatus();
                break;
            default:
                System.out.println("Unexpected behaviour. Please contact the Support Team.");
                break;
        }
    }

    private void setWaitingInputStatus() {
        System.out.println("\nWrite action (buy, fill, take, remaining, exit): ");
        currentStatus = Status.WAITING_INPUT;

    }

    private void chooseActionFromUser(String input) {
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

        Button currentAction = setUserInputToTheExistingButtons(input);

        switch (currentAction) {
            case BUY:
                System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, " +
                        "3 - cappuccino, back - return to the main menu: ");
                currentStatus = Status.CHOOSING_A_COFFEE;
                break;
            case FILL:
                currentStatus = Status.ADDING_WATER;
                System.out.println("\nWrite how many ml of water you want to add: ");
                break;
            case TAKE:
                currentStatus = Status.TAKING_MONEY;
                System.out.printf("I gave you $%d\n", money);
                updateSuppliesAmount(0, 0, 0, 0, -money);
                setWaitingInputStatus();
                break;
            case STATUS:
                currentStatus = Status.OUTPUT_CURR_STATUS;
                printSuppliesAmount();
                setWaitingInputStatus();
                break;
            case EXIT:
                currentStatus = Status.EXITING;
                System.exit(0);
                break;
        }
    }

    private boolean validationUserInputToTheExistingButtons(String someInput) {
        boolean isValidInput = false;
        for (Button b : Button.values()) {
            if (someInput.equalsIgnoreCase(b.getDescription())) {
                isValidInput = true;
                break;
            }
        }
        return isValidInput;
    }

    private Button setUserInputToTheExistingButtons(String someInput) {
        Button currentButton = null;

        for (Button b : Button.values()) {
            if (someInput.equalsIgnoreCase(b.getDescription())) {
                currentButton = b;
                break;
            }
        }
        return currentButton;
    }

    private int validationUserInputToTheExistingTypeCoffee(String input) {
        int typeCoffee = 0;

        if (input.equalsIgnoreCase("back")) {
            return 0;
        }

        try {
            typeCoffee = Integer.parseInt(input);
            if (!((typeCoffee > 0) && (typeCoffee <= 3))) {
                System.out.println("Your input is wrong. Try again.");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Your input is wrong. Try again.");
            return -1;
        }
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

    private void clearAddingSupplies() {
        addWater = 0;
        addMilk = 0;
        addCoffee = 0;
        addCup = 0;
    }

    private int validationAddSupply(String input) {
        int addSupply = 0;

        try {
            addSupply = Integer.parseInt(input);
            if (!(addSupply >= 0)) {
                System.out.println("Your input is wrong.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Your input is wrong.");
            return -1;
        }

        return addSupply;
    }

    private void printSuppliesAmount() {
        System.out.println();
        System.out.printf("""
                The coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money
                """, waterSupply, milkSupply, coffeeSupply, disposableCup, money);
    }
}
