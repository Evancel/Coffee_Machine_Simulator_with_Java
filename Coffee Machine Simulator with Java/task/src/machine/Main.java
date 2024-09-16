package machine;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        //my_version_with_the_class_CoffeeMachine
        CoffeeMachine cm = new CoffeeMachine(scanner, 400,540,120,9,550);
        cm.work();
        scanner.close();
/*
        Machine coffeeMachine = new Machine(400, 540, 120, 9, 550);
        while (coffeeMachine.isWorking()) {
            coffeeMachine.processingUserInput(scanner.nextLine());
        }
        scanner.close();
 */
    }
}
