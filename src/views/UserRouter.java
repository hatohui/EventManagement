package views;

import components.FormattedTable;
import components.Confirmation;
import controller.user.UserBank;
import models.User;
import utils.*;

import java.time.LocalDate;

public class UserRouter {
    private static final UserInterface UI;
    private static final String TITLE = "LIBRARY MANAGEMENT SYSTEM";
    private static final String[] OPTIONS = {"  Add   ", "Update", "Remove","Show All", "Return"};

    static {
        UI = new UIBuilder(75)
                .topWall()
                .rightString(TITLE, 5)
                .emptyWall()
                .header("== MANAGE USERS ==").withColor("BRIGHT_YELLOW","BLACK")
                .emptyWall()
                .options(OPTIONS).withColor("BRIGHT_WHITE","BLACK")
                .emptyWall()
                .bottomWall()
                .setDefaultColor("BRIGHT_BLUE")
                .saveAndReturn();
    }

    public static int deploy() {
        UI.deploy();
        return Input.getInteger("Your option",1,5);
    }

    public static void add(UserBank ub) {
        do {
            String ID;
            do {
                ID = IntegerID.getWithPrefix("U");
            } while (ub.have(ID));
            String name = Input.getString("Input new user's name",
                    "^[a-zA-Z]+([\\s'-][a-zA-Z]+)*$");
            LocalDate dateOfBirth = Input.getDate("Input new user's birthday");
            String phoneNumber = Input.getString("Input the user's phone number",
                    "^\\+?[0-9. ()-]{7,15}$");
            String email = Input.getString("Input the user's email",
                    "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
            ub.add(new User(ID, name, dateOfBirth, phoneNumber, email, true));
        } while (Confirmation.deploy("Add another User?"));
    }

    public static void delete(UserBank ub) {
        String ID = Input.getString("Book ID");
        if (!Confirmation.deploy("Confirm deleting User with ID " + ID + "?")) return;
        ub.delete(ID);
        System.out.println();
        UIComponents.loadingBarWithColor("Updating the database...",
                20, 100, "BRIGHT_YELLOW");
        System.out.println(Input.successMessage("Successfully updated the database, Here's the new state:"));
        getActiveTable(ub);
        Input.enterToContinue();
    }

    public static void getActiveTable(UserBank ub) {
        int count = 1;
        for (User user :ub.getActive()) {
            int[] sizes = {7, 25, 15, 4, 15, 15 ,4};
            String[] colors = {"BRIGHT_BLUE", "", "", "", "", "BOOL"};
            FormattedTable ft = new FormattedTable(sizes, colors);
            System.out.print("| " + count + " ");
            ft.printWithColor(user.toString(), ",");
            count++;
        }
    }

    public static void update(UserBank userMan) {
        if (userMan.isEmpty())
            throw new IllegalStateException("No user found, create one first!");
        String ID = Input.getString("Enter User's ID");
        //check if exist
        if (!userMan.have(ID))
            throw new IllegalArgumentException("Invalid ID");
        User preFix = userMan.get(ID);
        User data = new User(preFix.getID(),
                preFix.getName(),
                preFix.getBirthday(),
                preFix.getPhoneNumber(),
                preFix.getEmail(),
                preFix.isActive()
        );
        while (true) {
            try {
                switch (UserUpdateRouter.deploy(ID.toUpperCase())) {
                    case 1:
                        UserUpdateRouter.adjustName(data);
                        break;
                    case 2:
                        UserUpdateRouter.adjustBirthday(data);
                        break;
                    case 3:
                        UserUpdateRouter.adjustPhoneNumber(data);
                        break;
                    case 4:
                        UserUpdateRouter.adjustEmail(data);
                        break;
                    case 5:
                        UserUpdateRouter.adjustAvailability(data);
                        break;
                    case 6:
                        UserUpdateRouter.confirmChange(userMan, data);
                        return;
                    case 7:
                        if (!UserUpdateRouter.antiAccidentalLeave()) break;
                        UserUpdateRouter.resetChanges();
                        return;
                }
            } catch (Exception e) {
                Input.printError(e.getMessage());
            }
        }
    }

    public static void showAll(UserBank userMan) {
        //to look nice
        UIComponents.loadingBarWithColor("Fetching the database...",
                15, 50, "BRIGHT_YELLOW");

        Input.printSuccess("Current Users:");
        int count = 1;
        int[] sizes = {7, 25, 15, 15, 15 , 5};
        String[] color2 = {"MAGENTA", "MAGENTA", "MAGENTA","MAGENTA","MAGENTA","MAGENTA"};
        FormattedTable ft = new FormattedTable(sizes, color2);
        System.out.print("| No ");
        ft.printWithColor("UserID,Name,Birthday,Mobile,Email,Act",",");
        String[] colors = {"BRIGHT_BLUE", "", "", "", "", "BOOL"};
        ft = new FormattedTable(sizes,colors);

        //main print
        for (User user: userMan.getAllSortedByID()) {
            System.out.printf("| %2d " ,count);
            ft.printWithColor(user.toString(),",");
            count++;
        }
        Input.enterToContinue();
    }
}
