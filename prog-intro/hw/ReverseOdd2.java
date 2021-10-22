import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ReverseOdd2 {

    public static void main(String[] args) {
        ArrayList<int[]> lines = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            Scanner lineScan = new Scanner(line);
            //or only numbers=line.split("\\s+"));
            int[] numbers = new int[10];
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
        for (int i = lines.size() - 1; i >= 0; i--) {
            int[] numbers = lines.get(i);
            for (int j = numbers.length - 1; j >= 0; j--) {
                if((j+i)%2==1){
                    System.out.print(numbers[j] +" ");
                }
            }
            System.out.println();
        }
    }
}
