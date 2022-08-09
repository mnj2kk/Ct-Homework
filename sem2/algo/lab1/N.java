import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class N {
    public static class ScanlineObject implements Comparable<ScanlineObject> {
        int type;
        int coord;

        public ScanlineObject(int type, int coord) {
            this.coord = coord;
            this.type = type;
        }

        @Override
        public String toString() {
            return "ScanlineObject{" +
                    "type=" + type +
                    ", coord=" + coord +
                    '}';
        }

        @Override
        public int compareTo(ScanlineObject o) {
            int diff = this.coord - o.coord;
            if(diff!=0){
                return diff;
            }
            else{
                return (type-o.type);
            }
        }

    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        List<ScanlineObject> arr = new ArrayList<>();
        for (int i = 0; i < n; i ++) {
            int h1 = scan.nextInt();
            int m1 = scan.nextInt();
            int s1 = scan.nextInt();
            int t1 = 3600 * h1 + 60 * m1 + s1;
            int h2 = scan.nextInt();
            int m2 = scan.nextInt();
            int s2 = scan.nextInt();
            int t2 = 3600 * h2 + 60 * m2 + s2;
            if(t2>t1){
                arr.add(new ScanlineObject(1,t1));
                arr.add(new ScanlineObject(3,t2));
            }
            else {
                arr.add(new ScanlineObject(1,0));
                arr.add(new ScanlineObject(3,t2));
                arr.add(new ScanlineObject(1,t1));
                arr.add(new ScanlineObject(3,24*3600));
            }
        }
        arr.sort(ScanlineObject::compareTo);
        //System.err.println(Arrays.toString(arr));
        int c = 0;
        int ans =0;
        for (int i =0;i< arr.size();i++) {
            ScanlineObject obj = arr.get(i);
            if(c==n){
                ans+=(arr.get(i).coord-arr.get(i-1).coord);
            }
            if (obj.type == 1) {
                c++;
            } else if (obj.type == 3) {
                c--;
            }

        }
        System.out.println(ans);
    }
}
