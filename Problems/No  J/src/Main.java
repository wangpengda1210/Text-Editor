import java.util.*;

public class Main {

    public static void processIterator(String[] array) {
        // write your code here
        ListIterator<String> iterator = Arrays.asList(array).listIterator();

        while (iterator.hasNext()) {
            iterator.next();
        }

        while (iterator.hasPrevious()) {
            String item = iterator.previous();
            if (item.startsWith("J")) {
                System.out.println(item.substring(1));
            }
        }
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        processIterator(scanner.nextLine().split(" "));
    }
}