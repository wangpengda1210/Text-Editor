import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        List<String> first = Arrays.asList(scanner.nextLine().split(" "));
        List<String> second = Arrays.asList(scanner.nextLine().split(" "));

        System.out.print(Collections.indexOfSubList(first, second) + " ");
        System.out.print(Collections.lastIndexOfSubList(first, second));
    }
}