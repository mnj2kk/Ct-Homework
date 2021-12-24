import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class Two{
    public static void  main(String[] args){
    Scanner scan = new Scanner(System.in);
    List<String> list = new ArrayList<>();
    int n = scan.nextInt();
    list.add("0");
    list.add("1");
    for(int i =1;i<n;i++){
        list.addAll(reverse(list));
        for (int j =0;j< list.size();j++){
            if(j<list.size()/2){
            list.set(j,"0" +list.get(j));
            }
            else{
              list.set(j,"1" +list.get(j));
            }
        }
    }
    for(int i =0;i<list.size();i++){
        System.out.println(list.get(i));
    }
    }
    public static List<String> reverse(List<String> list ){
         List<String> tmp = new  ArrayList<String>();
        for(int i =list.size()-1;i>=0;i--){
            tmp.add(list.get(i));
        }
        return tmp;
    }
}
