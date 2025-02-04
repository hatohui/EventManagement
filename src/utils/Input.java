package utils;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Utility for getting inputs, data passed will
 * get checked for null, validity before returned to a value.
 * <p>
 * The code follows a certain formatting, change the source
 * code if you want your own style of inputs.
 */
public class Input {
    //scanner
    private static final Scanner scanner = new Scanner(System.in);

    public static void enterToContinue() {
        String str = ColorWrapper.YELLOW + "\n   [ PRESS ENTER TO CONTINUE... ]" + ColorWrapper.RESET ;
        System.out.println(str);
        scanner.nextLine();
    }

    public static String formatLength(String string, int length) {
        string = string.strip();
        StringBuilder result = new StringBuilder();
        if (string.length() < length) {
            result.append(string);
            result.append(" ".repeat(length - string.length()));
        } else if (string.length() == length) {
            result.append(string);
        } else {
            result.append(string, 0, length - 3);
            result.append("...");
        }
        return result.toString();
    }

    public static String formatLengthPad(String string, int length, int padding) {
        string = string.strip();
        if ((length - padding*2) < 5) throw new IllegalStateException("Invalid Length");
        StringBuilder result = new StringBuilder();
        String pad = " ".repeat(Math.max(0, padding));
        result.append(pad);

        length = length - padding*2;
        if (string.length() < length) {
            result.append(string);
            result.append(" ".repeat(length - string.length()));
        } else if (string.length() == length) {
            result.append(string);
        } else {
            result.append(string, 0, length - 3);
            result.append("...");
        }
        result.append(pad);

        return result.toString();
    }

    public static void printError(String error) {
        separator();
        System.out.print(errorMessage(error));
    }

    public static void printSuccess(String message) {
        separator();
        System.out.print(successMessage(message));
    }

    public static void printWarning(String message) {
        separator();
        System.out.print(warningMessage(message));
    }

    public static String warningMessage(String message) {
        String errorString = "\u001B[43m \u001B[30mWARNING \u001B[0m ";
        return errorString + message + "\n";
    }

    public static String errorMessage(String error) {
        String errorString = "\u001B[41;1m \u001B[30mERROR \u001B[0m ";
        return errorString + error + "\n";
    }

    public static String successMessage(String message) {
        String errorString = "\u001B[42;1m \u001B[30mSUCCESS \u001B[0m ";
        return errorString + message + "\n";
    }

    public static void separator() {
        System.out.println("──────────────────────────────────────────────────────────────────────────────");
    }
    /* HANDLE STRINGS*/


    public static String getStringNoConstraint(String message) {
        if (message == null)
            throw new IllegalArgumentException("No message found.");
        System.out.print("\u001B[34;1m " + message + " \u001B[0m>  ");
        return scanner.nextLine().trim();
    }

    public static LocalDate getDate(String message) {
        LocalDate dateOfBirth;
        while (true) {
            try {
                dateOfBirth = LocalDate.parse(Input.getString(
                        message + "  (yyyy-mm-dd)",
                        "\\d{4}-\\d{2}-\\d{2}"));
                break;
            } catch (Exception e) {
                Input.printError("Invalid date");
            }
        }
        return dateOfBirth;
    }

    private static String getString(String message, boolean allowSpecial, String regex) {
        //message
        if (message == null)
            throw new IllegalArgumentException("No message found.");

        //getInput
        while (true) {
            try {
                System.out.print("\u001B[34;1m " + message + " \u001B[0m>  ");
                String str = scanner.nextLine().trim();

                //check null
                if (str.isEmpty()) {
                    throw new IllegalArgumentException("Input can't be empty, try again.");
                }

                //check contain any special symbols
                if (!allowSpecial)
                    if (!str.matches("[\\w ]+"))
                        throw new IllegalArgumentException("Input can only " +
                                "contain letters, numbers and '_', try again.");

                //check regex
                if (regex != null) {
                    if (!str.matches(regex))
                        throw new IllegalArgumentException("Invalid input format.");
                }

                return str;
            } catch (Exception e) {
                separator();
                System.out.println(errorMessage(e.getMessage()));
            }
        }
    }

    /**
     * Return a string after printing details on what to input
     * to the console. The String inputted can only contain
     * letters [a-z][A-Z], numbers [0-9], and '_' by default. You
     * can change this by adding a true parameter.
     * <p>
     *     <i><b>Example: Input.getString("foo", true);</b></i>
     * <p>
     * The message will get printed in this form by default:
     * <br>
     *          <i><b>> [YourMessage]: [Input]</b></i>
     * @param message the string to display. if null throw
     *                illegalArgumentException.
     * @return - a string containing only letters a-z, A-Z, numbers
     * 0-9 and '_'
     * @throws IllegalArgumentException when no message found.
     */
    public static String getString(String message) {
        return getString(message, false, null);
    }

    /**
     * Return a string after printing details on what to input
     * to the console.
     * <p>
     * The message will get printed in this form by default:
     * <br>
     *          <i><b> [YourMessage]: [Input]</b></i>
     * <p>
     * @param message the string to display. if null throw
     *                illegalArgumentException.
     * @param allowSpecial set true to allow special symbols on the string,
     *              you can remove or set this parameter to false
     *              if special symbols are unwanted.
     * @return a string including special symbols.
     * @throws IllegalArgumentException when no message found.
     */
    public static String getString(String message, boolean allowSpecial) {
        return getString(message, allowSpecial, null);
    }

    /**
     * Return a string after printing details on what to input
     * and checking whether the input matches given regex to the console.
     * <p>
     * The message will get printed in this form by default:
     * <br>
     *          <i><b> [YourMessage]: [Input]</b></i>
     * <p>
     * @param message the string to display. if null throws
     *                illegalArgumentException.
     * @param regex takes in regex that force the input to match.
     * @return a string formatted with the regex given
     * @throws IllegalArgumentException when no message found.
     */
    public static String getString(String message, String regex) {
        return getString(message, true, regex);
    }

    /* HANDLE NUMBERS*/

    /* INTEGERS */

    /**
     * Return an integer after printing details on what to
     * input. Checking the range from floor to ceiling
     * <p>
     * @param message the string to display, if null throws
     *                illegalArgumentException
     * @param floor the lower bound of the integer expected.
     * @param ceiling the upper bound of the integer expected.
     * @return an integer within the range given
     * @throws IllegalArgumentException when no message was given
     */
    public static int getInteger(String message, int floor, int ceiling) {
        //print message
        if (message == null)
            throw new IllegalArgumentException("No message found.");

        //check valid floor and ceiling
        if (floor > ceiling) {
            throw new IllegalArgumentException("Floor must be lower than ceiling");
        } else if ((floor != 0 || ceiling != 0) && floor == ceiling) {
            throw new IllegalArgumentException(("Floor must < than ceiling"));
        }

        //getInput
        while (true) {
            try {
                System.out.print("\u001B[34;1m " + message + " \u001B[0m>  ");
                int number = Integer.parseInt(scanner.nextLine().trim());

                //check range
                if (!(floor == 0 && ceiling == 0)) {
                    if (number < floor || number > ceiling)
                        throw new IllegalStateException("Number is in invalid range. The " +
                                "range is " + floor + " - " + ceiling);
                }

                return number;
            } catch (Exception e) {
                separator();
                if (e instanceof NumberFormatException) {
                    System.out.println(errorMessage("Please input an integer."));
                } else System.out.println(errorMessage(e.getMessage()));
            }
        }
    }

    /**
     * Return an integer after printing details on what integer to
     * input with a bound.
     * <p>
     * @param message the string to display, if null throws
     *                illegalArgumentException
     * @param bound defines the range of expected integer.
     * <p>Example: <b><i>Input.getInteger("foo",10)</i></b></p> returns
     *              an integer from 0-10.
     * @return an integer ranging from 0 to the given bound.
     * @throws IllegalArgumentException when no message found.
     */
    public static int getInteger(String message, int bound) {
        return getInteger(message, 0, bound);
    }

    /**
     * Return an integer after printing details on what to
     * input.
     * <p>
     * @param message the string to display, if null throws
     *                illegalArgumentException
     * @return an integer
     * @throws IllegalArgumentException when no message found.
     */
    public static int getInteger(String message) {
        return getInteger(message, 0, 0);
    }

    /* DOUBLE */
    /**
     * Return a double after printing details on what to
     * input. Checking the range from floor to ceiling
     * <p>
     * @param message the string to display, if null throws
     *                illegalArgumentException
     * @param floor the lower bound of the double expected.
     * @param ceiling the upper bound of the double expected.
     * @return a double within the range given.
     * @throws IllegalArgumentException when no message found.
     */
    public static double getDouble(String message, double floor, double ceiling) {
        //print message
        if (message == null)
            throw new IllegalArgumentException("No message found.");

        //check valid floor and ceiling
        if (floor > ceiling) {
            throw new IllegalArgumentException("Floor must be lower than ceiling");
        } else if ((floor != 0 || ceiling != 0) && floor == ceiling) {
            throw new IllegalArgumentException(("Floor "));
        }

        //getInput
        while (true) {
            try {
                System.out.print("\u001B[34;1m " + message + " \u001B[0m>  ");
                double number = Double.parseDouble(scanner.nextLine());

                //check range
                if (!(floor == 0 && ceiling == 0)) {
                    if (number < floor || number > ceiling)
                        throw new IllegalStateException(String.format("Number is in" +
                                " invalid range. The range is %.2f - %.2f", floor, ceiling));
                }

                return number;
            } catch (Exception e) {
                separator();
                if (e instanceof NumberFormatException) {
                    System.out.println(errorMessage("Please input a double."));
                } else System.out.println(errorMessage(e.getMessage()));
            }
        }
    }

    /**
     * Return a double after printing details on what double to
     * input with a bound.
     * <p>
     * @param message the string to display, if null throws
     *                illegalArgumentException
     * @param bound defines the range of expected double.
     * <p>Example: <b><i>Input.getDouble("foo",10.1)</i></b></p> returns
     *              a double from 0-10.1.
     * @return a double ranging from 0 to the given bound.
     * @throws IllegalArgumentException when no message found.
     */
    public static double getDouble(String message, int bound) {
        return getDouble(message, 0, bound);
    }

    /**
     * Return a double after printing details on what to
     * input.
     * <p>
     * @param message the string to display, if null throws
     *                illegalArgumentException
     * @return a double
     * @throws IllegalArgumentException when no message is found
     */
    public static double getDouble(String message) {
        return getDouble(message, 0, 0);
    }

    /* FLOAT */
    /**
     * Return a float after printing details on what to
     * input. Checking the range from floor to ceiling
     * <p>
     * @param message the string to display, if null throws
     *                illegalArgumentException
     * @param floor the lower bound of the float expected.
     * @param ceiling the upper bound of the float expected.
     * @return a float within the given range
     * @throws IllegalArgumentException when no message is found.
     */
    public static float getFloat(String message, float floor, double ceiling) {
        //print message
        if (message == null)
            throw new IllegalArgumentException("No message found.");

        //check valid floor and ceiling
        if (floor > ceiling) {
            throw new IllegalArgumentException("Floor must be lower than ceiling");
        } else if ((floor != 0 || ceiling != 0) && floor == ceiling) {
            throw new IllegalArgumentException(("Floor "));
        }

        //getInput
        while (true) {
            try {
                System.out.print("\u001B[34;1m " + message + " \u001B[0m>  ");
                float number = Float.parseFloat(scanner.nextLine());

                //check range
                if (!(floor == 0 && ceiling == 0)) {
                    if (number < floor || number > ceiling)
                        throw new IllegalStateException(String.format("Number is in" +
                                " invalid range. The range is %.2f - %.2f", floor, ceiling));
                }

                return number;
            } catch (Exception e) {
                separator();
                if (e instanceof NumberFormatException) {
                    System.out.println(errorMessage("Please input a float."));
                } else System.out.println(errorMessage(e.getMessage()));
            }
        }
    }

    /**
     * Return a float after printing details on what float to
     * input with a bound.
     * <p>
     * @param message the string to display, if null throws
     *                illegalArgumentException
     * @param bound defines the range of expected float.
     * <p>Example: <b><i>Input.getFloat("foo",10.1)</i></b></p> returns
     *              a float from 0-10.1.
     * @return a float ranging from 0 to the given bound
     * @throws IllegalArgumentException when no message is found
     */
    public static float getFloat(String message, int bound) {
        return getFloat(message, 0, bound);
    }

    /**
     * Return a float after printing details on what to
     * input.
     * <p>
     * @param message the string to display, if null throws
     *                illegalArgumentException
     * @return a float
     * @throws IllegalArgumentException when no message is found
     */
    public static float getFloat(String message) {
        return getFloat(message, 0, 0);
    }

    /* CHARACTER */
    /**
     * Return the first character after printing details on what to
     * input.
     * <p>
     * @param message the string to display, if null throws
     *                illegalArgumentException
     * @return a character
     * @throws IllegalArgumentException when no message is found.
     */
    public static char getChar(String message) {

        if (message == null)
            throw new IllegalArgumentException("No message found.");

        while (true) {
            try {
                System.out.print("\u001B[34;1m " + message + " \u001B[0m>  ");
                return scanner.nextLine().trim().charAt(0);
            } catch (Exception e) {
                separator();
                if (e instanceof IndexOutOfBoundsException) {
                    System.out.println(errorMessage("Can't be empty, please input a character."));
                } else System.out.println(errorMessage(e.getMessage()));
            }
        }
    }
}