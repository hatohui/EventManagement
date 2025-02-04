package views;

import components.Confirmation;
import controller.book.BookLibrary;
import models.Book;
import utils.ColorWrapper;
import utils.Input;
import utils.UIBuilder;
import utils.UserInterface;

import java.util.HashMap;

public class BookUpdateRouter {
    private static UserInterface UI;
    private static final String TITLE = "LIBRARY MANAGEMENT SYSTEM";
    private static final String[] OPTIONS_2 = {"  Book's title  ", "  Author's name  ", "Publication year",
            "   Publisher   ", "     ISBN     ", " Availability ", "Confirm Change", "     Return     "};
    private static final HashMap<String, String> changes = new HashMap<>();

    public static void init(String ID) {
        UI = new UIBuilder(75)
                .topWall()
                .rightString(TITLE, 5)
                .emptyWall()
                .header("== ADJUSTING BOOK DETAILS ==").withColor("BRIGHT_YELLOW", "BLACK")
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
        };
        return Input.getInteger("Your option", 1, 8);
    }

    public static void resetChanges() {
        changes.clear();
    }

    public static void adjustAuthor(Book data) {
        String old = data.getAuthor();
        String name = Input.getStringNoConstraint("Enter new author's name");
        if (name.isEmpty()) {
            Input.printWarning("No data changed!");
            Input.enterToContinue();
            return;
        }
        data.setAuthor(name);
        changes.put("Author", old + " -> " + data.getAuthor());
    }

    public static void adjustPublicYear(Book data) {
        int old = data.getPubicYear();
        int year;
        while (true) {
            try {
                String input = Input.getStringNoConstraint("Enter new public year");
                if (input.isEmpty()) {
                    Input.printWarning("No data changed!");
                    Input.enterToContinue();
                    return;
                }
                year = Integer.parseInt(input);
                if (year < 0) throw new IllegalArgumentException("Invalid Year");
                break;
            } catch (Exception e) {
                Input.printError(e.getMessage());
            }
        }
        data.setPubicYear(year);
        changes.put("Public Year", old + " -> " + data.getPubicYear());
    }

    public static void adjustAvailability(Book data) {
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

    public static void adjustISBN(Book data) {
        String old = data.getISBN();
        String input = Input.getStringNoConstraint("Enter new author's name");
        if (input.isEmpty()) {
            Input.printWarning("No data changed!");
            Input.enterToContinue();
            return;
        }
        data.setISBN(input);
        changes.put("ISBN", old + " -> " + data.getISBN());
    }

    public static void adjustPublisher(Book data) {
        String oldPublisher = data.getPublisher();
        String input = Input.getStringNoConstraint("Enter new author's name");
        if (input.isEmpty()) {
            Input.printWarning("No data changed!");
            Input.enterToContinue();
            return;
        }
        data.setPublisher(input);
        changes.put("Publisher", oldPublisher + " -> " + data.getPublisher());
    }

    public static void confirmChange(BookLibrary bookMan, Book data) {
        if (Confirmation.deploy("Confirm changes?"))
            bookMan.update(data.getID(), data);
    }

    public static void adjustTitle(Book data) {
        String oldTitle = data.getTitle();
        String input = Input.getStringNoConstraint("Enter new Book's title");
        if (input.isEmpty()) {
            Input.printWarning("No data changed!");
            Input.enterToContinue();
            return;
        }
        data.setTitle(input);
        changes.put("Title", oldTitle + " -> " + data.getTitle());
    }

    public static void getChanges() {
        StringBuilder toPrint = new StringBuilder();
        for (String key : changes.keySet()) {
            toPrint.append(" ")
                    .append(ColorWrapper.addColor(key, "BRIGHT_GREEN"))
                    .append(": ")
                    .append(ColorWrapper.addColor(changes.get(key),"BRIGHT_YELLOW", "BLACK")).append("\n");
        }
        Input.separator();
        System.out.print(toPrint);
    }

    public static boolean antiAccidentLeave() {
        if (changes.isEmpty()) return true;
        return Confirmation.deploy("You have unsaved changes? Confirm to leave?");
    }
}
