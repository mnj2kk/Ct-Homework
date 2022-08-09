import java.util.*;
import java.util.function.Consumer;

public class I {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        if (scan.nextInt() == 1) {
            scan.nextLine();
            String line = scan.nextLine();
            ArrayList<Integer> code = new ArrayList<>();
            int i = 1;
            genCode(line, code, i);
            StringBuilder builder = new StringBuilder();
            for (Integer integer : code) {
                builder.append(integer);
            }
            System.out.println(builder);

        } else {
            scan.nextLine();
            String in = scan.nextLine();
            StringBuilder line = new StringBuilder();
            ArrayList<Integer> code = new ArrayList<>();
            int i = 1;
            while (true) {
                if ( in.length()!= i-1 &&((i & (i - 1)) == 0)) {
                } else if (in.length() > i-1) {
                    line.append(in.charAt(i -1)) ;
                } else {
                    break;
                }
                i++;
            }
             i = 1;
            genCode(line.toString(), code, i);
            int counter=-1;
            for (int j = 0; j < in.length(); j++) {
                if(in.charAt(j)-'0'!=code.get(j) &&((j==0)||(((j+1) & (j )) == 0))){
                    counter+=(j+1);
                }

            }
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < in.length() ; j++) {
                if((j==0)||(((j+1) & (j )) == 0)){
                }
                else if(j==counter){
                    builder.append(in.charAt(counter)=='0'?'1':'0');
                }
                else{
                    builder.append(in.charAt(j));
                }
            }
            System.out.println(builder);
        }
    }

    private static void genCode(String line, ArrayList<Integer> code, int i) {
        int added=0;
        while (true) {
            if ( line.length()!= i-added-1 &&((i & (i - 1)) == 0)) {
                code.add(0);
                added++;
            } else if (line.length() > i-added-1) {
                code.add(line.charAt(i-added -1) - '0');
            } else {
                break;
            }
            i++;
        }
        //[0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0]
        //[0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1]
        for ( i = 1; i <= code.size(); i++) {
            if ( ((i & (i - 1)) == 0)) {
                int v =0;
                for (int j = i; j <= code.size(); j+=(2*i)) {
                    for (int k = j; k < j+i; k++) {
                        if(code.size()>=k) {
                            v = v ^ code.get(k-1);
                        }
                        else{
                            break;
                        }
                    }
                }
                code.set(i-1,v);
            }
        }
        //If code was rigth [0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1]
        // My case          [1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0]
                           //0 +1     4           +8                  +15

    }
}
