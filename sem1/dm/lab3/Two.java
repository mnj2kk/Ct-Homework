import java.util.Scanner;

public class Two {
    static int n;
    static StringBuilder ans = new StringBuilder();
    static int counter=0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        int end = 2 << (n - 1);
        int[ ] tmp =new int[n];
        tmp[0]=0;
        genVect(1,1,tmp);
        tmp[0]=1;
        genVect(1, 1, tmp);
        System.out.println(counter);
        System.out.println(ans);
    }

    public static void genVect(int length, int position, int[] currComb) {
        if (length == n) {
            for (int j : currComb) {
                ans.append(j);
            }
            counter++;
            ans.append(System.lineSeparator());
        } else {
            for (int i = 0; i < 2; i++) {
                if(i==1 && currComb[position-1]==0){
                    currComb[position] = i;
                    genVect(length + 1, position + 1, currComb);
                }
                else if(i==0) {
                    currComb[position] = i;
                    genVect(length + 1, position + 1, currComb);
                }
            }
        }
    }

}
