package coffeeMachine;

/**
 * The Messages class contains the static constant strings used throughout the CoffeeMachine application.
 * These messages are displayed to the user during interaction with the coffee machine.
 */
public class Messages {
    public final static String MENU = "\nWrite action (buy, fill, take, remaining, exit): ";
    public final static String BEVERAGE_MENU = """
            What do you want to buy?\s
             1 - espresso,
             2 - latte,
             3 - cappuccino,
             back - return to the main menu:""";
    public static final String STATUS_INFO_FORMAT = """
            The coffee machine has:
            %d ml of water
            %d ml of milk
            %d g of coffee beans
            %d disposable cups
            $%d of money""";
    public static final String TAKE_MONEY_MESSAGE = "I gave you $%d";
    public final static String ENOUGH_RESOURCES = "I have enough resources, making you a coffee!";
    public final static String NOT_ENOUGH_WATER = "Sorry, not enough water!";
    public final static String NOT_ENOUGH_MILK = "Sorry, not enough milk!";
    public final static String NOT_ENOUGH_COFFEE = "Sorry, not enough coffee beans!";
    public final static String NOT_ENOUGH_CUPS = "Sorry, not enough disposable cups!";
    public final static String ADD_WATER_PROMPT = "Write how many ml of water you want to add:";
    public final static String ADD_MILK_PROMPT = "Write how many ml of milk you want to add:";
    public final static String ADD_COFFEE_PROMPT = "Write how many grams of coffee beans you want to add:";
    public final static String ADD_CUPS_PROMPT = "Write how many disposable cups you want to add:";
    public final static String WRONG_CHOICE = """
            Your input is wrong:
            to buy a coffee type BUY,
            to fill supplies type FILL,
            to take money type TAKE,
            to check all supplies type REMAINING,
            to exit type EXIT.
            
            Please try again.""";
    public final static String WRONG_INPUT = "Your input is wrong. Try again.";
}
