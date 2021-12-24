import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Five {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<String> list = new ArrayList<>();
        int n = scan.nextInt();
        int k = scan.nextInt();
        for (int i = 0; i < k; i++) {
            list.add(String.valueOf(i));
        }
        for(int i =1;i<n;i++){
            ArrayList<String> tmp = new ArrayList<>();
            for (int j = 1; j < k; j++) {
                if(j%2==1){
                    tmp.addAll(reverse(list));
                }
                else{
                    tmp.addAll(list);
                }

            }
            list.addAll(tmp);
            for (int j =0;j< list.size();j++){
                for (int l = 0; l < k; l++) {
                    if(j<(list.size()*(l+1))/k){
                        list.set(j,l  + list.get(j));
                        break;
                    }
                }
            }
        }
        for (String s : list) {
            System.out.println(s);
        }
    }
    public static List<String> reverse(List<String> list ){
        List<String> tmp = new ArrayList<String>();
        for(int i =list.size()-1;i>=0;i--){
            tmp.add(list.get(i));
        }
        return tmp;
    }
}
