import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> numbers = new ArrayList<>(Arrays.asList(scanner.nextLine().split(" ")));
        int count = scanner.nextInt();
        for (int i = 0; i < count; i++) {
            Collections.swap(numbers, scanner.nextInt(), scanner.nextInt());
        }

        for (String number : numbers) {
            System.out.print(number + " ");
        }
    }
}