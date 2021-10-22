
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Reverse {

    public static void main(String[] args) {
        ArrayList<int[]> lines = new ArrayList<>();
        MyScanner scan = new MyScanner(System.in);
        while (true) {
            try {
                if (!scan.hasNextLine()) break;
                String line = scan.nextLine();
                if(line!=null) {
                    MyScanner lineScan = new MyScanner(line);
                    //or only numbers=line.split("\\s+"));
                    int[] numbers = new int[10];
                    int counter = 0;
                    while (lineScan.hasNextInt()) {
                        if (counter + 1 >= numbers.length) {
                            numbers = Arrays.copyOf(numbers, counter * 2);
                        }
                        numbers[counter] = lineScan.nextInt();
                        counter++;
                    }
                    lineScan.close();
                    //remove nulls
                    numbers = Arrays.copyOf(numbers, counter);
                    lines.add(numbers);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = lines.size() - 1; i >= 0; i--) {
            int[] numbers = lines.get(i);
            for (int j = numbers.length - 1; j >= 0; j--) {
                System.out.print(numbers[j] + " ");
            }
            System.out.println();
        }
    }
}
