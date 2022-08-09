import scanner.MyScanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReverseOdd2 {

    public static void main(String[] args) throws IOException {
        ArrayList<int[]> lines = new ArrayList<>();
        MyScanner scan = new MyScanner(System.in);
        MyScanner lineScan;
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
             lineScan = new MyScanner(line);
            //or only numbers=line.split("\\s+"));
            int[] numbers = new int[1000];
            int counter = 0;
            while (lineScan.hasNextInt()) {
                if(counter+1>= numbers.length){
                    numbers= Arrays.copyOf(numbers,counter*2);
                }
                numbers[counter] = lineScan.nextInt();
                counter++;
            }
            lineScan.close();

            //remove nulls
            numbers = Arrays.copyOf(numbers, counter);
            lines.add(numbers);
        }
        scan.close();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = lines.size() - 1; i >= 0; i--) {
            int[] numbers = lines.get(i);
            for (int j = numbers.length - 1; j >= 0; j--) {
                if((j+i)%2==1){
                   stringBuilder.append(numbers[j]).append(" ");
                }
            }
            if(i>0){
                stringBuilder.append(System.lineSeparator());
            }
        }
        System.out.println(stringBuilder);
    }
}
