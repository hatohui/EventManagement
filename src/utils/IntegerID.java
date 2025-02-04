package utils;

public class IntegerID {
    private static int MAX = 5;

    public static void setLength(int length) {
        MAX = length;
    };

    public static String get() {
        String str = String.valueOf((int)(Math.random() * ((int) Math.pow(10, MAX) + 1)));
        if (str.length() < MAX) {
            return "0".repeat(MAX - str.length()) + str;
        }
        return str;
    }

    public static String get(int max) {
        return String.valueOf(Math.random() * (max + 1));
    }

    public static String getWithPrefix(String prefix, int max) {
        return prefix + IntegerID.get(max);
    }

    public static String getWithPrefix(String prefix) {
        return prefix + IntegerID.get();
    }
}
