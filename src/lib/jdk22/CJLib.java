package lib.jdk22;

import java.util.Scanner;
import java.util.Random;
import java.security.SecureRandom;

public class CJLib {
    private static final Scanner SCAN = new Scanner( System.in );
    private static final Random PRAND = new Random();
    private static final SecureRandom SRAND = new SecureRandom();

    // Print Statement //
    public static void cout(Object o) {
        System.out.print(o+"\n");
    }

    // Console Input //
    public static String cin(String prompt) {
        System.out.print(prompt);
        String s = SCAN.next();
        return s.trim();
    }

    public static int cin_i32(String prompt) {
        String num_str = cin(prompt);
        try {
            int num = Integer.parseInt(num_str);
            return num;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Double cin_f32(String prompt) {
        String num_str = cin(prompt);
        try {
            Double num = Double.parseDouble(num_str);
            return num;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    // Random Numbers //
    public static int prand(int min, int max) {
        int num = PRAND.nextInt(max)+min;
        return num;
    }

    public static int srand(int min, int max) {
        int num = SRAND.nextInt(max)+min;
        return num;
    }
}
