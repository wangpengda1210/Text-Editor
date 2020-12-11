import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        List<String> characters = Arrays.asList(scanner.nextLine().split(" "));

        System.out.println(Collections.frequency(characters, scanner.next()));
    }
}