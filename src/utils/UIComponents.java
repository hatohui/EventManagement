package utils;

import java.util.stream.Stream;

/**
 * Consists of components for creating console app's UIs.
 *
 * @author hatohui
 */
public class UIComponents {
    private static char completedLoading = '█';
    private static char incompleteLoading = '░';
    private static char defaultMaterial = '═';
    private static char vertical = '║';
    private static char topLeft = '╔';
    private static char topRight = '╗';
    private static char horizontal = '═';
    private static char bottomLeft = '╚';
    private static char bottomRight = '╝';
    private static char background = ' ';
    private int horizontalLength = 70;

    /**
     *Constructor for UIBuilder.
     */
    public UIComponents() {
    }
    /**
     * Constructor for UIBuilder.
     * <p>If contain options for one letter needs at least 15 horizontal length.
     * Which increase by the length of the option's string.
     *
     * @param horizontalLength an integer > 2 and <= 200 that indicates
     *                         the length of the UI horizontally
     * @throws IllegalArgumentException when the received integer is < 2 or > 200.
     */
    public UIComponents(int horizontalLength) {
        if (horizontalLength < 2 || horizontalLength > 200)
            throw new IllegalArgumentException("size must be > 2 and <= 200");
        this.horizontalLength = horizontalLength;
    }

    /**
     * Set the horizontal length of the components to
     * an integer size.
     *
     * @param horizontalLength an Integer indicating
     *                         its length.
     * @throws IllegalArgumentException - when the received integer
     * is < 2 or > 200
     */
    public void setHorizontalLength(int horizontalLength) {
        if (horizontalLength < 2 || horizontalLength > 200)
            throw new IllegalArgumentException("Length must be more then" +
                    " or equal to2" +
                    "and less than or equal to 200");
        this.horizontalLength = horizontalLength;
    }

    /**
     * Print into the console an animated loading bar: █████░░░░░░
     * <br>(change the values in the source code if you want a different bar)
     *
     * @param message  - a string to display before the loading bar.
     * @param loadTime - an integer indicates amount of tick
     *                 for the loading bar.
     * @param interval - an integer that indicatses time period
     *                 per tick (in milliseconds).
     */
    public static void loadingBar(String message, int loadTime, int interval) {
        try {
            StringBuilder builder = new StringBuilder();

            if (!message.isEmpty()) message += "\t";

            //generates the stream
            Stream.generate(() -> incompleteLoading)
                    .limit(loadTime)
                    .forEach(builder::append);

            //loads
            for (int i = 0; i < loadTime; ++i) {
                builder.replace(i, i + 1, java.lang.String.valueOf(completedLoading));
                java.lang.String loading = "\r" + message + builder;
                System.out.print(loading);
                Thread.sleep(interval);
            }

            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Print into the console an animated loading bar: █████░░░░░░
     * <br>Yes, with colors. You can choose a different color by adding an extra
     * parameter for color at the end.
     * message, loadTime, interval, color
     * <br>(change the values in the source code if you want a different bar)
     *
     * @param message  - a string to display before the loading bar.
     * @param loadTime - an integer indicates amount of tick
     *                 for the loading bar.
     * @param interval - an integer that indicates time period
     *                 per tick (in milliseconds).
     */
    public static void loadingBarWithColor(String message, int loadTime, int interval, String Color) {
        try {
            StringBuilder builder = new StringBuilder();

            if (!message.isEmpty()) message += "\t";

            //generates the stream
            Stream.generate(() -> ' ')
                    .limit(loadTime)
                    .forEach(builder::append);

            //loads
            double percentage = 0;
            double count = 0;
            for (int i = 0; i < loadTime; ++i) {
                builder.replace(i, i + 1, String.valueOf(completedLoading));
                count++;
                percentage = ((double) 100 / loadTime) * count;
                String loading = String.format("\r%s|%s%s\u001B[0m| %.2f", message,
                        ColorWrapper.getColor(Color), builder, percentage);
                System.out.print(loading + '%');
                Thread.sleep(interval);
            }

            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Print into the console an animated green loading bar: █████░░░░░░
     * <br>Yes, with colors. You can choose a different color by adding an extra
     * parameter for color at the end.
     * message, loadTime, interval, color
     * <br>(change the values in the source code if you want a different bar)
     *
     * @param message  - a string to display before the loading bar.
     * @param loadTime - an integer indicates amount of tick
     *                 for the loading bar.
     * @param interval - an integer that indicates time period
     *                 per tick (in milliseconds).
     */
    public void loadingBarWithColor(String message, int loadTime, int interval) {
       loadingBarWithColor(message, loadTime, interval, "GREEN");
    }

    /**
     * Return a separator made of given character in desired length.
     *
     * @param material a character that the separator is made of.
     * @return a String
     * @throws IllegalArgumentException when amount of material is <= 0.
     */
    public String separator(char material) {
        StringBuilder builder = new StringBuilder();

        //generate the separator
        Stream.generate(() -> material)
                .limit(horizontalLength)
                .forEach(builder::append);

        return builder.toString();
    }

    /**
     * Return a separator made of given character with default
     * length and default material in source.
     * @return a String
     */
    public String separator() {
        return separator(defaultMaterial);
    }

    /**
     * Return a String of UIs ceiling.
     * @return a String.
     */
    public String topWall() {
        StringBuilder topWall = new StringBuilder();

        topWall.append(topLeft);

        Stream.generate(() -> horizontal)
                .limit(horizontalLength - 2)
                .forEach(topWall::append);

        topWall.append(topRight);

        return topWall.toString();
    }

    /**
     * Return a string of the UIs floor.
     * @return a String flooring for your UI.
     */
    public String bottomWall() {
        StringBuilder bottomWall = new StringBuilder();

        bottomWall.append(bottomLeft);

        Stream.generate(() -> horizontal)
                .limit(horizontalLength - 2)
                .forEach(bottomWall::append);

        bottomWall.append(bottomRight);

        return bottomWall.toString();
    }

    /**
     * Return a String of a wall with two ends into the console.
     * @return a String with two end walled.
     */
    public String emptyWall() {
        StringBuilder emptyLine = new StringBuilder();

        emptyLine.append(vertical);
        Stream.generate(() -> background)
                .limit(horizontalLength - 2)
                .forEach(emptyLine::append);
        emptyLine.append(vertical);

        return emptyLine.toString();
    }

    /**
     * Return a string Header with given title into the console.
     *
     * @param title a string that will be printed into the console.
     * @return a String for the Header.
     * @throws IllegalStateException when the title's length is too big compared
     *                               to the default horizontal length.
     */
    public String header(String title) {
        int padding = (horizontalLength - 2 - title.length()) / 2;

        if (horizontalLength - 2 < title.length())
            throw new IllegalStateException("Title won't fit, title length is: "
                    + title.length() + " while we only have " + (horizontalLength - 2));

        StringBuilder header = new StringBuilder();

        header.append(vertical);
        Stream.generate(() -> background)
                .limit(padding)
                .forEach(header::append);
        header.append(title);

        if ((horizontalLength - title.length()) % 2 == 1) padding++;
        Stream.generate(() -> background)
                .limit(padding)
                .forEach(header::append);
        header.append(vertical);

        return header.toString();
    }

    /**
     * Return a string of options wrapped by the walls within the UI.
     * <p></p>
     *
     * @param options a String array containing options
     *                to be printed.
     * @return a String containing the UI to be printed.
     * @throws IllegalArgumentException when options is empty or when
     *                                  the passed argument is null.
     * @throws IllegalStateException when the text can't fit into the UI.
     */
    public String options(String[] options) {
        //handle null
        if (options == null)
            throw new IllegalArgumentException("Passed argument is null.");

        //handle empty
        if (options.length == 0)
            throw new IllegalArgumentException("Passed String array can't be empty.");

        int count = 1;
        StringBuilder result = new StringBuilder();

        for (String option : options) {
            //initializes
            StringBuilder string = new StringBuilder();
            String opt = "[" + count + "]";

            //handle not enough space
            if ((horizontalLength - 2) < option.length())
                throw new IllegalStateException("The option's text length cannot fit" +
                        ", optLength is " + option.length() + " and horizontalLength is " +
                        (horizontalLength - 2));

            int padding = (horizontalLength - 2 - option.length()) / 2;

            //check if enough space to have option bar
            if (padding < 6)
                throw new IllegalStateException("Not enough length to fit in the option's text");

            int pad = padding / 2;
            //building the string
            string.append(vertical);
            Stream.generate(() -> background)
                    .limit(pad - 3)
                    .forEach(string::append);

            string.append(opt);

            if (!(padding % 2 == 0)) pad += 1;

            Stream.generate(() -> background)
                    .limit(pad)
                    .forEach(string::append);

            string.append(option);

            if ((horizontalLength - option.length()) % 2 == 1) padding++;
            Stream.generate(() -> background)
                    .limit(padding)
                    .forEach(string::append);
            string.append(vertical);

            result.append("\n").append(string);
            count++;
        }

        return result.deleteCharAt(0).toString();
    }

    /**
     * Return a string the option wrapped by the walls within the UI.
     * <p></p>
     *
     * @param optionText a String containing option
     *                to be printed.
     * @param buttonText a String containing the text inside the button
     * @return a String containing the UI to be printed.
     * @throws IllegalArgumentException when options is empty or when
     *                                  the passed argument is null.
     * @throws IllegalStateException when the text can't fit into the
     * UI.
     */
    public String option(String buttonText, String optionText) {
        //handle null
        if (optionText == null)
            throw new IllegalArgumentException("Passed argument is null.");

        if (optionText.length() > horizontalLength - 5)
            throw new IllegalArgumentException("The passed argument is too long for " +
                    "the current box's size");

        //initializes
        StringBuilder result = new StringBuilder();
        String button = "[" + buttonText + "]";
        int padding = (horizontalLength - 2 - optionText.length()) / 2;
        //check padding size;
        if (padding < 6) throw new IllegalStateException("Passed argument is too long for" +
                "the current box's size");

        int pad = padding / 2;
        if (pad - 2 <= buttonText.length()) throw new IllegalStateException("Passed buttonText" +
                "too long for current length");

        result.append(vertical);
        Stream.generate(() -> background)
                .limit(pad)
                .forEach(result::append);
        result.append(button);
        //fill in missing spots
        if (!(padding % 2 == 0)) pad += 1;
        Stream.generate(() -> background)
                .limit(pad - buttonText.length() - 2)
                .forEach(result::append);
        result.append(optionText);
        if ((horizontalLength - optionText.length()) % 2 == 1) padding++;
        Stream.generate(() -> background)
                .limit(padding)
                .forEach(result::append);
        result.append(vertical);

        return result.toString();
    }

    /**Return a string within the UI containing the text
     * given, the code will automatically create a new line
     * for the UI if the text is too long, and separates accordingly.
     * @param text the String given to be printed
     * @param padding an integer indicating the space left empty
     *                on two sides.
     * @throws IllegalArgumentException when the argument passed are null
     * or empty
     * @return a String containing the UI to be printed.
     * */
    public String description(String text, int padding) {
        if (text.isEmpty())
            throw new IllegalArgumentException("Argument passed is empty or null, if you wanted" +
                    " an empty line use emptyWall");

        String[] texts = text.split(" ");
        int index = 0;
        int length = text.length();

        StringBuilder string = new StringBuilder();
        int sizeOfText = horizontalLength - 2 - padding*2;

        do {
            string.append(vertical);
            Stream.generate(() -> background)
                    .limit(padding)
                    .forEach(string::append);

            String toPrint = "";
            while (true) {
                if (index == texts.length) break;
                if (toPrint.length() + texts[index].length() + 1 <= sizeOfText) {
                    toPrint = toPrint + texts[index] + " ";
                    index++;
                } else break;
            }

            length -= toPrint.length();
            string.append(toPrint);
            int sup = sizeOfText - toPrint.length();
            Stream.generate(()->background)
                    .limit(padding+sup)
                    .forEach(string::append);
            string.append(vertical);
            string.append("\n");

        } while (length > 0);

        return string.substring(0, string.length()-1);
    }

    /**
     * Return a string containing given text on the left side padded
     * with the given amount.
     * @param text - the String given to be printed.
     * @param padding - an integer indicating the padding
     * @return a String containing the UI to be printed.
     * @throws IllegalArgumentException when argument passed is null.
     * @throws IllegalStateException when the text cannot fit.
    */
    public String leftString(String text, int padding) {
        if (text.isEmpty())
            throw new IllegalArgumentException("Argument passed is null");

        if (padding > horizontalLength-2)
            throw new IllegalStateException("Padding passed cannot be wider" +
                    "than the space for text");
        if (text.length() > horizontalLength - 2)
            throw new IllegalStateException("Text passed is too long.");

        StringBuilder string = new StringBuilder();
        string.append(vertical);
        Stream.generate(() -> background)
                .limit(padding)
                .forEach(string::append);
        string.append(text);
        Stream.generate(() -> background)
                .limit(horizontalLength-text.length()-padding-2)
                .forEach(string::append);
        string.append(vertical);
        return string.toString();
    }

    /**
     * Return a string containing given text on the right side padded
     * with the given amount.
     * @param text - the String given to be printed.
     * @param padding - an integer indicating the padding
     * @return a String containing the UI to be printed.
     * @throws IllegalArgumentException when argument passed is null.
     * @throws IllegalStateException when the text cannot fit.
     */
    public String rightString(String text, int padding) {
        if (text.isEmpty())
            throw new IllegalArgumentException("Argument passed is null");

        if (padding > horizontalLength-2)
            throw new IllegalStateException("Padding passed cannot be wider" +
                    "than the space for text");
        if (text.length() > horizontalLength - 2)
            throw new IllegalStateException("Text passed is too long.");

        StringBuilder string = new StringBuilder();
        string.append(vertical);
        Stream.generate(() -> background)
                .limit(horizontalLength-text.length()-padding-2)
                .forEach(string::append);
        string.append(text);
        Stream.generate(() -> background)
                .limit(padding)
                .forEach(string::append);
        string.append(vertical);
        return string.toString();
    }

    /**Return an error String to print into the console.
    * @param error the error String to be printed.
    */
    public String errorMessage(String error) {
        String errorString = "\u001B[41;1m \u001B[30mERROR \u001B[0m ";
        return errorString + error;
    }

    /**Return a success String to print into the console.
     * @param message the success String to be printed.
     */
    public String successMessage(String message) {
        String errorString = "\u001B[42;1m \u001B[30mSUCCESS \u001B[0m ";
        return errorString + message;
    }

    /**Return a warning String to print into the console.
     * @param message the warning String to be printed.
     */
    public String warningMessage(String message) {
        String warningString = "\u001B[43;1m \u001B[30mWARNING \u001B[0m ";
        return warningString + message;
    }

    /* A bunch of setters and getters */

    public static char getVertical() {
        return vertical;
    }

    public static char getCompletedLoading() {
        return completedLoading;
    }

    public static char getIncompleteLoading() {
        return incompleteLoading;
    }

    public static char getDefaultMaterial() {
        return defaultMaterial;
    }

    public static char getTopLeft() {
        return topLeft;
    }

    public static char getTopRight() {
        return topRight;
    }

    public static char getHorizontal() {
        return horizontal;
    }

    public static char getBottomLeft() {
        return bottomLeft;
    }

    public static char getBottomRight() {
        return bottomRight;
    }

    public static char getBackground() {
        return background;
    }

}