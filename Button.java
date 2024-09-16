package machine;

public enum Button {
    BUY("buy"),
    FILL("fill"),
    TAKE("take"),
    STATUS("remaining"),
    EXIT("exit");

    private String description;

    Button(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
}
