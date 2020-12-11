import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        ArrayList<ArrayList<Integer>> table = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                row.add(scanner.nextInt());
            }
            table.add(row);
        }

        Collections.rotate(table, scanner.nextInt());

        for (ArrayList<Integer> row : table) {
            for (int i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}