
import scanner.MyScanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReverseAbc2 {

    public static void main(String[] args) {
        ArrayList<int[]> lines = new ArrayList<>();
        MyScanner scan = new MyScanner(System.in);
        while (true) {
            try {
                if (!scan.hasNextLine()) break;
                String line = scan.nextLine();
                if (line != null) {
                    MyScanner lineScan = new MyScanner(line);
                    //or only numbers=line.split("\\s+"));
                    int[] numbers = new int[1000];
                    int counter = 0;
                    while (lineScan.hasNextAbc2Int()) {
                        if (counter + 1 >= numbers.length) {
                            numbers = Arrays.copyOf(numbers, counter * 2);
                        }
                        numbers[counter] = lineScan.nextAbc2Int();
                        counter++;
                    }
                    lineScan.close();
                    //remove nulls
                    numbers = Arrays.copyOf(numbers, counter);
                    lines.add(numbers);
                }
            } catch (IOException e) {
                System.out.println("IO problems " + e.getMessage());
            }

        }

        try {
            scan.close();
        } catch (IOException e) {
            System.out.println("sScanner Closing  problems" + e.getMessage());

        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = lines.size() - 1; i >= 0; i--) {
            int[] numbers = lines.get(i);
            for (int j = numbers.length - 1; j >= 0; j--) {
                stringBuilder.append(numbers[j]).append(" ");
            }
            if(i>0){
                stringBuilder.append(System.lineSeparator());
            }
        }
        System.out.println(stringBuilder);

    }
}
