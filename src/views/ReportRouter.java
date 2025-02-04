package views;

import components.FormattedTable;
import controller.loan.LoanBank;
import models.Book;
import models.Loan;
import utils.Input;
import utils.UIBuilder;
import utils.UIComponents;
import utils.UserInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ReportRouter {
    private static final UserInterface UI;
    private static final String TITLE = "LIBRARY MANAGEMENT SYSTEM";
    private static final String[] OPTIONS = {
            "Show Borrowed Books", "Show Overdue Books", "  Total Activities  ", "      Return      "};

    static {
        UI = new UIBuilder(75)
                .topWall()
                .rightString(TITLE, 5)
                .emptyWall()
                .header("== REPORT MENU ==").withColor("BRIGHT_YELLOW", "BLACK")
                .emptyWall()
                .options(OPTIONS).withColor("BRIGHT_WHITE", "BLACK")
                .emptyWall()
                .bottomWall()
                .setDefaultColor("BRIGHT_BLUE")
                .saveAndReturn();
    }

    public static int deploy() {
        UI.deploy();
        return Input.getInteger("Your input", 1, 4);
    }

    public static void showAllBorrowedBook(LoanBank loanMan) {
        HashMap<String, Book> map = loanMan.getBorrowedBooks();
        UIComponents.loadingBarWithColor("Fetching the database...",
                15, 50, "BRIGHT_YELLOW");
        if (map.isEmpty()) throw new IllegalStateException("Empty database.");
        Input.printSuccess("Currently Borrowed Books:");
        int count = 1;
        int[] sizes = {7, 7, 25, 15, 15, 15, 20, 5};
        String[] color2 = {"MAGENTA", "MAGENTA", "MAGENTA","MAGENTA","MAGENTA","MAGENTA","MAGENTA", "MAGENTA"};
        FormattedTable ft = new FormattedTable(sizes, color2);
        System.out.print("| No ");
        ft.printWithColor("UserID, BookID,Book Title, Author,PY,Publisher,ISBN,act",",");
        String[] colors = {"BRIGHT_BLUE", "CYAN", "", "", "", "", "", "BOOL"};
        ft = new FormattedTable(sizes,colors);
        for (String userID: map.keySet()) {
            System.out.printf("| %2d " ,count);
            ft.printWithColor( userID + "," + map.get(userID).toString(),",");
            count++;
        }
        Input.enterToContinue();
    }

    public static void showOverDueBooks(LoanBank loanMan) {
        Collection<Loan> overdue = loanMan.getOverdue(LocalDate.now());
        if (overdue.isEmpty()) throw new IllegalStateException("No data found.");

        UIComponents.loadingBarWithColor("Fetching the database...",
                15, 50, "BRIGHT_YELLOW");
        Input.printSuccess("Currently Overdue:");

        int count = 1;
        int[] sizes = {7, 7, 7, 10, 10, 5};
        String[] color2 = {"MAGENTA", "MAGENTA", "MAGENTA","MAGENTA","MAGENTA", "MAGENTA"};
        FormattedTable ft = new FormattedTable(sizes, color2);
        System.out.print("| No ");
        ft.printWithColor("TranID, UserID,BookID, BorrowDate,ReturnDate,act",",");
        String[] colors = {"BRIGHT_BLUE", "CYAN", "", "", "", "BOOL"};
        ft = new FormattedTable(sizes,colors);
        for (Loan loan: overdue) {
            System.out.printf("| %2d " ,count);
            ft.printWithColor(loan.toString(),",");
            count++;
        }
        Input.enterToContinue();
    }

    public static void showActivities(LoanBank loanMan) {
        LocalDate after = Input.getDate("Period After");
        LocalDate before = Input.getDate("Period Before");
        ArrayList<Loan> activities = loanMan.getAllByPeriod(after, before);
        if (activities.isEmpty()) throw new IllegalStateException("Data not found");
        UIComponents.loadingBarWithColor("Fetching the database...",
                15, 50, "BRIGHT_YELLOW");
        Input.printSuccess("All activities:");
        int count = 1;
        int[] sizes = {7, 7, 7, 10, 10, 5};
        String[] color2 = {"MAGENTA", "MAGENTA", "MAGENTA","MAGENTA","MAGENTA","MAGENTA"};
        FormattedTable ft = new FormattedTable(sizes, color2);
        System.out.print("| No ");
        ft.printWithColor("TranID, UserID,BookID, BorrowDate,ReturnDate,act",",");
        String[] colors = {"BRIGHT_BLUE", "CYAN", "", "", "", "BOOL"};
        ft = new FormattedTable(sizes,colors);
        for (Loan loan: activities) {
            System.out.printf("| %2d " ,count);
            ft.printWithColor( loan.toString(),",");
            count++;
        }
        Input.printSuccess("Found " + activities.size() + "activities");
        Input.enterToContinue();
    }
}
