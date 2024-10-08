package coffeeMachine;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;

    public UserInterface(){
        this.scanner = new Scanner(System.in);
    }

    public String getUserInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void exit(){
        scanner.close();
    }
}
