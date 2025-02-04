package utils;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Validator {

    private static final String ISBN_10_REGEX = "^(\\d{9}[\\dXx])$";
    private static final String ISBN_13_REGEX = "^(97[89]\\d{10})$";

    public static boolean isLooselyValidISBN(String isbn) {
        return isbn.matches(ISBN_10_REGEX) || isbn.matches(ISBN_13_REGEX);
    }

    public static boolean isValidISBN10(String isbn) {
        if (isbn == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(ISBN_10_REGEX);
        Matcher matcher = pattern.matcher(isbn);
        if (!matcher.matches()) {
            return false;
        }
        return isValidISBN10Checksum(isbn);
    }

    // Validate ISBN-13 format and checksum
    public static boolean isValidISBN13(String isbn) {
        if (isbn == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(ISBN_13_REGEX);
        Matcher matcher = pattern.matcher(isbn);
        if (!matcher.matches()) {
            return false;
        }
        return isValidISBN13Checksum(isbn);
    }

    // Checksum validation for ISBN-10
    private static boolean isValidISBN10Checksum(String isbn) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }
        char checksum = isbn.charAt(9);
        sum += (checksum == 'X' || checksum == 'x') ? 10 : (checksum - '0');
        return sum % 11 == 0;
    }

    // Checksum validation for ISBN-13
    private static boolean isValidISBN13Checksum(String isbn) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += (isbn.charAt(i) - '0') * (i % 2 == 0 ? 1 : 3);
        }
        int checksum = 10 - (sum % 10);
        if (checksum == 10) {
            checksum = 0;
        }
        return checksum == (isbn.charAt(12) - '0');
    }

    public static boolean isValidISBN(String isbn) {
        return isValidISBN10(isbn) || isValidISBN13(isbn);
    }
}
