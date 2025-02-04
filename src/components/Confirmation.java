package components;

import utils.Input;

public class Confirmation {

    /**return true if yes, false if no
     * */
    public static boolean deploy(String message) {
        return switch (Input.getInteger(message + " (0: no | 1: yes)", 0, 1)) {
            case 1 -> true;
            case 0 -> false;
            default -> throw new IllegalStateException("Illegal Case");
        };
    }

    public static boolean deploy() {
        return deploy("Return to menu?");
    }
}