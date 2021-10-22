

public class SumLong {
    public static void main(String[] args) {
        StringBuilder strBuilder = new StringBuilder();
        for (String arg : args) {
            strBuilder.append(" ").append(arg);
        }
        //To don't check last symbol
        strBuilder.append(" ");
        String str = strBuilder.toString();
        long sum = 0;
        int numberStart = 0;
        int numberEnd;
        boolean isReadingNumber = false;
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            if (Character.isWhitespace(symbol) && isReadingNumber) {
                isReadingNumber = false;
                numberEnd = i;
                sum += Long.parseLong(str.substring(numberStart, numberEnd));
            } else if (!Character.isWhitespace(symbol) && !isReadingNumber) {
                isReadingNumber = true;
                numberStart = i;

            }

        }
        System.out.println(sum);
    }
}

