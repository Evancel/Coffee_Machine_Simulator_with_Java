package machine;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Machine machine = new Machine(400, 540, 120, 9, 550);
        while (machine.isWorking()) {
            machine.processingUserInput(scanner.nextLine());
        }
        scanner.close();
    }
}
