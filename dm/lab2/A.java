import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class A {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long res = 0;
        int n = scan.nextInt();
        ArrayList<Long> arr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            long v = scan.nextLong();
            arr.add(v);
        }
        arr.sort(null);
        while (arr.size()>2){
            long v1 = arr.get(0);
            long v2 = arr.get(1);
            long sum = v1 + v2;
            arr.add(sum);
            res+=sum;
            arr.remove(0);
            arr.remove(0);
            arr.sort(null);
        }
        res+=arr.get(0);
        res+=arr.get(1);
        System.out.println(res);

    }
}
