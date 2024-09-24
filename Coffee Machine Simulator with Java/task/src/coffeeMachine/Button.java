package coffeeMachine;

/**
 * The Button enum represents all possible actions like:
 * - to buy a drink,
 * - to fill amount of water, milk, coffee, disposable cups,
 * - to take all money from the coffee machine,
 * - to see remaining of supplies,
 * - to exit.
 * It holds details like description.
 * It provides methods to get the description of a button.
 */
public enum Button {
    BUY("buy"),
    FILL("fill"),
    TAKE("take"),
    STATUS("remaining"),
    EXIT("exit");

    private final String description;

    /**
     * Constructs a new Button with the given description.
     *
     * @param description  set the description for a button
     */

    Button(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
}
