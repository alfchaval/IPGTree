package mtg.judge.ipgtree.Utilities;

import java.util.Random;

//If you took time to see my code, you are free to unlock the ftp transfer, it's not hard
public class Code {

    private final static String allowedChars = "yich54k0z98g7mvrwdbjexotulp2nsaq61f3";
    private final static int codeLenght = 8;

    public static String generateCode() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(codeLenght);
        for(int i = 0; i < codeLenght; i++) {
            stringBuilder.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        return stringBuilder.toString();
    }

    public static String generateAnswer(String code) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(code.length());
        int index1 = allowedChars.indexOf(code.charAt(0));
        int index2 = allowedChars.indexOf(code.charAt(code.length() - 1));
        int lastPosition;
        if(index1 >= 0 && index2 >= 0) {
            lastPosition = (index1 + index2) % allowedChars.length();
            stringBuilder.append(allowedChars.charAt(lastPosition));
        }
        for (int i = 1; i < code.length(); i++) {
            index1 = allowedChars.indexOf(code.charAt(i));
            index2 = allowedChars.indexOf(code.charAt(i-1));
            if(index1 >= 0 && index2 >= 0) {
                lastPosition = (index1 + index2) % allowedChars.length();
                stringBuilder.append(allowedChars.charAt(lastPosition));
            }
        }
        return stringBuilder.toString();
    }

    public static boolean check(String code, String answer) {
        return generateAnswer(code).equals(answer);
    }
}
