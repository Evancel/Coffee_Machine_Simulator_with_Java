import coffeeMachine.CoffeeMachine;
import machine.Machine;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CoffeeMachine cm = new CoffeeMachine(scanner, 400,540,120,9,550);
        cm.work();
        scanner.close();

        /*
        Machine machine = new Machine(400, 540, 120, 9, 550);
        while (machine.isWorking()) {
            machine.processingUserInput(scanner.nextLine());
        }
        scanner.close();
         */
    }
}
