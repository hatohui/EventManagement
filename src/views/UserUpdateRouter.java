package views;

import components.Confirmation;
import controller.user.UserBank;
import models.User;
import utils.ColorWrapper;
import utils.Input;
import utils.UIBuilder;
import utils.UserInterface;

import java.time.LocalDate;
import java.util.HashMap;

public class UserUpdateRouter {
    private static UserInterface UI;
    private static final String TITLE = "LIBRARY MANAGEMENT SYSTEM";
    private static final String[] OPTIONS_2 = {"  User's name  ", " Date of birth ", "  Phone number  ",
            "     Email     ", " Availability ", "Confirm Change", "     Return     "};
    private static final HashMap<String, String> changes = new HashMap<>();

    public static void init(String ID) {
        UI = new UIBuilder(75)
                .topWall()
                .rightString(TITLE, 5)
                .emptyWall()
                .header("== ADJUSTING USER DETAILS ==").withColor("BRIGHT_YELLOW", "BLACK")
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
        if (!changes.isEmpty()) {
            System.out.print(ColorWrapper.addColor("CURRENT CHANGES:\n","RED"));
            getChanges();
        }
        return Input.getInteger("Your option", 1, 8);
    }

    public static void resetChanges() {
        changes.clear();
    }

    public static void adjustName(User data) {
        String old = data.getName();
        String name;
        while (true) {
            try {
                name = Input.getStringNoConstraint("Enter new user's name");
                if (name.isEmpty()) {
                    Input.printWarning("No data changed!");
                    Input.enterToContinue();
                    return;
                }
                if (!name.matches("^[a-zA-Z]+([\\s'-][a-zA-Z]+)*$"))
                    throw new IllegalArgumentException("Invalid Name");
                break;
            } catch (Exception e) {
                Input.printError(e.getMessage());
            }
        }
        data.setName(name);
        changes.put("Name", old + " -> " + data.getName());
    }

    public static void adjustBirthday(User data) {
        LocalDate old = data.getBirthday();
        LocalDate date;
        while (true) {
            try {
                String input = Input.getStringNoConstraint("Enter user's new Birthday (yyyy-mm-dd)");
                if (input.isEmpty()) {
                    Input.printWarning("No data changed!");
                    Input.enterToContinue();
                    return;
                }
                date = LocalDate.parse(input);
                break;
            } catch (Exception e) {
                Input.printError("Please enter a valid date");
            }
        }
        data.setBirthday(date);
        changes.put("Birthday", old + " -> " + data.getBirthday().toString());
    }

    public static void adjustPhoneNumber(User data) {
        String old = data.getName();
        String phoneNumber;
        while (true) {
            try {
                phoneNumber = Input.getStringNoConstraint("Enter new user's phone number");
                if (phoneNumber.isEmpty()) {
                    Input.printWarning("No data changed!");
                    Input.enterToContinue();
                    return;
                }
                if (phoneNumber.matches("^\\+?[0-9. ()-]{7,15}$"))
                    throw new IllegalArgumentException("Invalid Phone Number");
                break;
            } catch (Exception e) {
                Input.printError(e.getMessage());
            }
        }
        data.setPhoneNumber(phoneNumber);
        changes.put("Number", old + " -> " + data.getPhoneNumber());
    }

    public static void adjustEmail(User data) {
        String old = data.getEmail();
        String email;
        while (true) {
            try {
                email = Input.getStringNoConstraint("Enter users new email");
                if (email.isEmpty()) {
                    Input.printWarning("No data changed!");
                    Input.enterToContinue();
                    return;
                }
                if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))
                    throw new IllegalArgumentException("Invalid Mail Format");
                break;
            }catch (Exception e) {
                Input.printError(e.getMessage());
            }
        }

        data.setEmail(email);
        changes.put("Email", old + " -> " + data.getEmail());
    }

    public static void adjustAvailability(User data) {
        boolean old = data.isActive();
        Input.separator();
        System.out.println("""
                | 1 |: SET ACTIVE
                | 2 |: SET INACTIVE
                | 3 |: RETURN""");
        switch (Input.getInteger("Your input", 1, 3)) {
            case 1 -> data.setActive();
            case 2 -> data.setInactive();
            case 3 -> {
                Input.printWarning("No data changed");
                Input.enterToContinue();
                return;
            }
        }
        changes.put("available", old + " -> " + data.isActive());
    }

    public static void confirmChange(UserBank userMan, User data) {
        if (Confirmation.deploy("Confirm changes?")) userMan.update(data.getID(), data);
    }

    public static void getChanges() {
        StringBuilder toPrint = new StringBuilder();
        for (String key : changes.keySet()) {
            toPrint
                    .append(" ")
                    .append(ColorWrapper.addColor(key, "BRIGHT_GREEN"))
                    .append(": ")
                    .append(ColorWrapper.addColor(changes.get(key),"BRIGHT_YELLOW", "BLACK")).append("\n");
        }
        Input.separator();
        System.out.print(toPrint);
    }

    public static boolean antiAccidentalLeave() {
        if (changes.isEmpty()) return true;
        return Confirmation.deploy("You have unsaved changes? Confirm to leave?");
    }
}
