package TaskVK.Utils;

import java.util.Random;


public class RandomUtils {
    
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    
    private static final Random r = new Random();
    
    
    public static String randomizeContent(int len) {
        
        String combination = UPPER + LOWER + DIGITS;
        char[] text = new char[len];
        
        for (int i = 0; i < len; i++) {
            text[i] = combination.charAt(r.nextInt(combination.length()));
        }
        
        return String.valueOf(text);
    }
}
