import java.util.Arrays;
import java.util.Scanner;

public class M {
    public static class ScanlineObject implements Comparable<ScanlineObject> {
        int type;
        int start;
        int p;

        public ScanlineObject(int type, int coord) {
            this.start = coord;
            this.type = type;
        }

        public ScanlineObject(int type, int coord, int p) {
            this.start = coord;
            this.type = type;
            this.p = p;
        }

        @Override
        public String toString() {
            return "ScanlineObject{" +
                    "type=" + type +
                    ", start=" + start +
                    ", p=" + p +
                    '}';
        }

        @Override
        public int compareTo(ScanlineObject o) {
            int diff = this.start - o.start;
            if (diff != 0) {
                return diff;
            } else {
                return (type - o.type);
            }
        }

    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        ScanlineObject[] arr = new ScanlineObject[2 * n + m];
        for (int i = 0; i < 2 * n; i += 2) {
            int a = scan.nextInt();
            int b = scan.nextInt();
            arr[i] = new ScanlineObject(1, Math.min(a, b));
            arr[i + 1] = new ScanlineObject(3, Math.max(a, b));
        }
        int p = 0;
        for (int i = 2 * n; i < arr.length; i++) {
            arr[i] = new ScanlineObject(2, scan.nextInt(), p);
            p++;
        }

        Arrays.sort(arr);
        //System.err.println(Arrays.toString(arr));
        int c = 0;
        int[] sol = new int[m];
        for (ScanlineObject obj : arr) {
            if (obj.type == 1) {
                c++;
            } else if (obj.type == 3) {
                c--;
            } else if (obj.type == 2) {
                sol[obj.p] = c;
            }
        }
        for (var point : sol) {
            System.out.print(point + " ");
        }
    }
}
