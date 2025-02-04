package views;

import components.Confirmation;
import models.Loan;
import utils.Input;
import utils.UIBuilder;
import utils.UserInterface;

public class LoanUpdateRouter {
    private static UserInterface UI;
    private static final String TITLE = "LIBRARY MANAGEMENT SYSTEM";
    private static final String[] OPTIONS_2 = {"Extends One Week", "Extends by days", "  Set Active  " , "     Return     "};

    public static void init(String ID) {
        UI = new UIBuilder(75)
                .topWall()
                .rightString(TITLE, 5)
                .emptyWall()
                .header("== ADJUSTING LOAN DETAILS ==").withColor("BRIGHT_YELLOW", "BLACK")
                .header("Current ID: " + ID).withColor("BRIGHT_GREEN")
                .emptyWall()
                .options(OPTIONS_2).withColor("BRIGHT_WHITE", "BLACK")
                .emptyWall()
                .bottomWall()
                .setDefaultColor("BRIGHT_BLUE")
                .saveAndReturn();
    }

    public static int deploy(String id) {
        init(id);
        UI.deploy();
        return Input.getInteger("Your option", 1, 4);
    }

    public static void extendOneWeek(Loan data) throws IllegalAccessException {
        if (Confirmation.deploy("Extend this user's period by one week?")) {
            data.extendOneWeek();
            Input.printSuccess("Successfully updated the data");
            Input.enterToContinue();
        }
    }


    public static void adjustActive(Loan data) {
        Input.separator();
        System.out.println("""
                | 1 |: SET ACTIVE
                | 2 |: SET INACTIVE
                | 3 |: RETURN""");
        switch (Input.getInteger("Your input", 1, 3)) {
            case 1 -> {
                data.setActive();
                Input.printSuccess("Successfully updated status");
                Input.enterToContinue();
            }
            case 2 -> {
                data.setInactive();
                Input.printSuccess("Successfully updated status");
                Input.enterToContinue();
            }
        }
    }

    public static void extendManyDays(Loan data) {
        int input = Input.getInteger("Extend how many days? (max a month)",
                1,30);
        if (Confirmation.deploy("Extend this user's borrow duration by " + input + "days?")) {
            data.extendByDays(input);
            Input.printSuccess("Successfully adjusted returned date");
            Input.enterToContinue();
        }
    }
}
