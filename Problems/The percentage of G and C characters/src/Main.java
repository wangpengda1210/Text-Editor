import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        String line = new Scanner(System.in).nextLine().toLowerCase();
        int count = 0;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == 'g' || line.charAt(i) == 'c') {
                count++;
            }
        }

        System.out.println((double) count / line.length() * 100);
    }
}