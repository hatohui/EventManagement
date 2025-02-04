package utils;
import java.util.HashMap;
import java.util.Map;

public class ColorWrapper {
    // Text Colors
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BRIGHT_BLACK = "\u001B[30;1m";
    public static final String BRIGHT_RED = "\u001B[31;1m";
    public static final String BRIGHT_GREEN = "\u001B[32;1m";
    public static final String BRIGHT_YELLOW = "\u001B[33;1m";
    public static final String BRIGHT_BLUE = "\u001B[34;1m";
    public static final String BRIGHT_MAGENTA = "\u001B[35;1m";
    public static final String BRIGHT_CYAN = "\u001B[36;1m";
    public static final String BRIGHT_WHITE = "\u001B[37;1m";

    // Background Colors
    public static final String BACKGROUND_BLACK = "\u001B[40m";
    public static final String BACKGROUND_RED = "\u001B[41m";
    public static final String BACKGROUND_GREEN = "\u001B[42m";
    public static final String BACKGROUND_YELLOW = "\u001B[43m";
    public static final String BACKGROUND_BLUE = "\u001B[44m";
    public static final String BACKGROUND_MAGENTA = "\u001B[45m";
    public static final String BACKGROUND_CYAN = "\u001B[46m";
    public static final String BACKGROUND_WHITE = "\u001B[47m";

    public static final String BRIGHT_BACKGROUND_BLACK = "\u001B[40;1m";
    public static final String BRIGHT_BACKGROUND_RED = "\u001B[41;1m";
    public static final String BRIGHT_BACKGROUND_GREEN = "\u001B[42;1m";
    public static final String BRIGHT_BACKGROUND_YELLOW = "\u001B[43;1m";
    public static final String BRIGHT_BACKGROUND_BLUE = "\u001B[44;1m";
    public static final String BRIGHT_BACKGROUND_MAGENTA = "\u001B[45;1m";
    public static final String BRIGHT_BACKGROUND_CYAN = "\u001B[46;1m";
    public static final String BRIGHT_BACKGROUND_WHITE = "\u001B[47;1m";

    private static final Map<String, String> colorMap = new HashMap<>();
    private static final Map<String, String> backgroundColorMap = new HashMap<>();

    static {
        colorMap.put("RESET", RESET);
        colorMap.put("BLACK", BLACK);
        colorMap.put("RED", RED);
        colorMap.put("GREEN", GREEN);
        colorMap.put("YELLOW", YELLOW);
        colorMap.put("BLUE", BLUE);
        colorMap.put("MAGENTA", MAGENTA);
        colorMap.put("CYAN", CYAN);
        colorMap.put("WHITE", WHITE);
        colorMap.put("BRIGHT_BLACK", BRIGHT_BLACK);
        colorMap.put("BRIGHT_RED", BRIGHT_RED);
        colorMap.put("BRIGHT_GREEN", BRIGHT_GREEN);
        colorMap.put("BRIGHT_YELLOW", BRIGHT_YELLOW);
        colorMap.put("BRIGHT_BLUE", BRIGHT_BLUE);
        colorMap.put("BRIGHT_MAGENTA", BRIGHT_MAGENTA);
        colorMap.put("BRIGHT_CYAN", BRIGHT_CYAN);
        colorMap.put("BRIGHT_WHITE", BRIGHT_WHITE);

        backgroundColorMap.put("BLACK", BACKGROUND_BLACK);
        backgroundColorMap.put("RED", BACKGROUND_RED);
        backgroundColorMap.put("GREEN", BACKGROUND_GREEN);
        backgroundColorMap.put("YELLOW", BACKGROUND_YELLOW);
        backgroundColorMap.put("BLUE", BACKGROUND_BLUE);
        backgroundColorMap.put("MAGENTA", BACKGROUND_MAGENTA);
        backgroundColorMap.put("CYAN", BACKGROUND_CYAN);
        backgroundColorMap.put("WHITE", BACKGROUND_WHITE);
        backgroundColorMap.put("BRIGHT_BLACK", BRIGHT_BACKGROUND_BLACK);
        backgroundColorMap.put("BRIGHT_RED", BRIGHT_BACKGROUND_RED);
        backgroundColorMap.put("BRIGHT_GREEN", BRIGHT_BACKGROUND_GREEN);
        backgroundColorMap.put("BRIGHT_YELLOW", BRIGHT_BACKGROUND_YELLOW);
        backgroundColorMap.put("BRIGHT_BLUE", BRIGHT_BACKGROUND_BLUE);
        backgroundColorMap.put("BRIGHT_MAGENTA", BRIGHT_BACKGROUND_MAGENTA);
        backgroundColorMap.put("BRIGHT_CYAN", BRIGHT_BACKGROUND_CYAN);
        backgroundColorMap.put("BRIGHT_WHITE", BRIGHT_BACKGROUND_WHITE);
    }


    /**Add color to the text of given String
     * @param string a String receiving colors
     * @param color a String that matches the colorCode
     * @return a String with color
     * @throws IllegalArgumentException when no color matches
     *          or when message is null
     * */
    public static String addColor(String string, String color) {
        if (string.isEmpty()) throw new IllegalArgumentException("Argument passed is empty");
        return switch (color.toUpperCase()) {
            case "BLACK" -> BLACK + string + RESET;
            case "RED" -> RED + string + RESET;
            case "GREEN" -> GREEN + string + RESET;
            case "YELLOW" -> YELLOW + string + RESET;
            case "BLUE" -> BLUE + string + RESET;
            case "MAGENTA" -> MAGENTA + string + RESET;
            case "CYAN" -> CYAN + string + RESET;
            case "WHITE" -> WHITE + string + RESET;
            case "BRIGHT_BLACK" -> BRIGHT_BLACK + string + RESET;
            case "BRIGHT_RED" -> BRIGHT_RED + string + RESET;
            case "BRIGHT_GREEN" -> BRIGHT_GREEN + string + RESET;
            case "BRIGHT_YELLOW" -> BRIGHT_YELLOW + string + RESET;
            case "BRIGHT_BLUE" -> BRIGHT_BLUE + string + RESET;
            case "BRIGHT_MAGENTA" -> BRIGHT_MAGENTA + string + RESET;
            case "BRIGHT_CYAN" -> BRIGHT_CYAN + string + RESET;
            case "BRIGHT_WHITE" -> BRIGHT_WHITE + string + RESET;
            default -> throw new IllegalArgumentException("Invalid Color.");
        };
    }

    /**Add background color to given String
     * @param string a String to display
     * @param color a String that matches the colorCode
     * @return a String with background color
     * @throws IllegalArgumentException when no color matches
     *          or when message is null
     * */
    public static String addBackgroundColor(String string, String color) {
        if (string.isEmpty()) throw new IllegalArgumentException("Argument passed is empty");

        return switch (color.toUpperCase()) {
            case "BLACK" -> BACKGROUND_BLACK + string + RESET;
            case "RED" -> BACKGROUND_RED + string + RESET;
            case "GREEN" -> BACKGROUND_GREEN + string + RESET;
            case "YELLOW" -> BACKGROUND_YELLOW + string + RESET;
            case "BLUE" -> BACKGROUND_BLUE + string + RESET;
            case "MAGENTA" -> BACKGROUND_MAGENTA + string + RESET;
            case "CYAN" -> BACKGROUND_CYAN + string + RESET;
            case "WHITE" -> BACKGROUND_WHITE + string + RESET;
            case "BRIGHT_BLACK" -> BRIGHT_BACKGROUND_BLACK + string + RESET;
            case "BRIGHT_RED" -> BRIGHT_BACKGROUND_RED + string + RESET;
            case "BRIGHT_GREEN" -> BRIGHT_BACKGROUND_GREEN + string + RESET;
            case "BRIGHT_YELLOW" -> BRIGHT_BACKGROUND_YELLOW + string + RESET;
            case "BRIGHT_BLUE" -> BRIGHT_BACKGROUND_BLUE + string + RESET;
            case "BRIGHT_MAGENTA" -> BRIGHT_BACKGROUND_MAGENTA + string + RESET;
            case "BRIGHT_CYAN" -> BRIGHT_BACKGROUND_CYAN + string + RESET;
            case "BRIGHT_WHITE" -> BRIGHT_BACKGROUND_WHITE + string + RESET;
            default -> throw new IllegalArgumentException("Invalid Background Color.");
        };
    }

    /**Add background color to given String
     * @param string a String to display
     * @param textColor String that matches the colorCode
     *                  for the text
     * @param backgroundColor String that matches the colorCode
     *                        for the background
     * @return a colored String with background color
     * @throws IllegalArgumentException when no color matches
     *          or when message is null
     * */
    public static String addColor(String string, String textColor, String backgroundColor) {
        if (string.isEmpty()) throw new IllegalArgumentException("Argument passed is empty");

        String textColorCode = switch (textColor.toUpperCase()) {
            case "BLACK" -> BLACK;
            case "RED" -> RED;
            case "GREEN" -> GREEN;
            case "YELLOW" -> YELLOW;
            case "BLUE" -> BLUE;
            case "MAGENTA" -> MAGENTA;
            case "CYAN" -> CYAN;
            case "WHITE" -> WHITE;
            case "BRIGHT_BLACK" -> BRIGHT_BLACK;
            case "BRIGHT_RED" -> BRIGHT_RED;
            case "BRIGHT_GREEN" -> BRIGHT_GREEN;
            case "BRIGHT_YELLOW" -> BRIGHT_YELLOW;
            case "BRIGHT_BLUE" -> BRIGHT_BLUE;
            case "BRIGHT_MAGENTA" -> BRIGHT_MAGENTA;
            case "BRIGHT_CYAN" -> BRIGHT_CYAN;
            case "BRIGHT_WHITE" -> BRIGHT_WHITE;
            default -> throw new IllegalArgumentException("Invalid Text Color.");
        };

        String backgroundColorCode = switch (backgroundColor.toUpperCase()) {
            case "BLACK" -> BACKGROUND_BLACK;
            case "RED" -> BACKGROUND_RED;
            case "GREEN" -> BACKGROUND_GREEN;
            case "YELLOW" -> BACKGROUND_YELLOW;
            case "BLUE" -> BACKGROUND_BLUE;
            case "MAGENTA" -> BACKGROUND_MAGENTA;
            case "CYAN" -> BACKGROUND_CYAN;
            case "WHITE" -> BACKGROUND_WHITE;
            case "BRIGHT_BLACK" -> BRIGHT_BACKGROUND_BLACK;
            case "BRIGHT_RED" -> BRIGHT_BACKGROUND_RED;
            case "BRIGHT_GREEN" -> BRIGHT_BACKGROUND_GREEN;
            case "BRIGHT_YELLOW" -> BRIGHT_BACKGROUND_YELLOW;
            case "BRIGHT_BLUE" -> BRIGHT_BACKGROUND_BLUE;
            case "BRIGHT_MAGENTA" -> BRIGHT_BACKGROUND_MAGENTA;
            case "BRIGHT_CYAN" -> BRIGHT_BACKGROUND_CYAN;
            case "BRIGHT_WHITE" -> BRIGHT_BACKGROUND_WHITE;
            default -> throw new IllegalArgumentException("Invalid Background Color.");
        };

        return textColorCode + backgroundColorCode + string + RESET;
    }


    public static String getColor(String color) {
        return colorMap.getOrDefault(color, RESET);
    }

    public static String getBackground(String backgroundColor) {
        return backgroundColorMap.getOrDefault(backgroundColor, RESET);
    }
}
