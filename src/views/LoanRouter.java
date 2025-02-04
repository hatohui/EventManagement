package views;

import components.Confirmation;
import components.FormattedTable;
import controller.loan.LoanBank;
import models.Loan;
import utils.*;

import java.time.LocalDate;

public class LoanRouter {
    private static final UserInterface UI;
    private static final String TITLE = "LIBRARY MANAGEMENT SYSTEM";
    private static final String[] OPTIONS = {"Lend a book", "Return a book", "Update loan", "Show all lent"  , "  Main menu  "};

    static {
        UI = new UIBuilder(75)
                .topWall()
                .rightString(TITLE, 5)
                .emptyWall()
                .header("== LEND A BOOK ==").withColor("BRIGHT_YELLOW", "BLACK")
                .emptyWall()
                .options(OPTIONS).withColor("BRIGHT_WHITE", "BLACK")
                .emptyWall()
                .bottomWall()
                .setDefaultColor("BRIGHT_BLUE")
                .saveAndReturn();
    }

    public static int deploy() {
        UI.deploy();
        return Input.getInteger("Your option", 1, 5);
    }

    public static void add(LoanBank loanMan) {
        do {
            String tranID;
            String userID;
            String bookID;
            do {
                tranID = IntegerID.getWithPrefix("T");
            } while (loanMan.have(tranID));
            while (true) {
                try {
                userID = Input.getStringNoConstraint("Enter User's ID");
                if (userID.isEmpty()) return;
                if (!loanMan.haveUser(userID))
                    throw new IllegalArgumentException("User Not Found");
                if (!loanMan.getUser(userID).isActive())
                    throw new IllegalStateException("Inactive User");
                break;
                } catch (Exception e) {
                    Input.printError(e.getMessage());
                }
            }
            Input.printSuccess("Found User: " + loanMan.getUser(userID).getName());
            while (true) {
                try {
                    bookID = Input.getStringNoConstraint("Enter Book's ID");
                    if (bookID.isEmpty()) return;
                    if (!loanMan.haveBook(bookID))
                        throw new IllegalArgumentException("Book Not Found");
                    if (!loanMan.getBook(bookID).isActive())
                        throw new IllegalStateException("Book Unavailable");
                    break;
                } catch (Exception e) {
                    Input.printError(e.getMessage());
                }
            }
            Input.printSuccess("Found Book: " + loanMan.getBook(bookID).getTitle());
            if (!Confirmation.deploy("Lend user " + userID +
                    " this book with ID " + bookID +  "?")) return;
            loanMan.add(new Loan(tranID, userID, bookID, LocalDate.now(),
                    LocalDate.now().plusDays(loanMan.getDefaultRentDays()),true));
            Input.printSuccess("Successfully created a new loan!");
        } while (Confirmation.deploy("Add another Loan?"));
    }

    public static void returnABook(LoanBank loanMan) {
        String ID = Input.getString("Enter Transaction ID");
        if (!loanMan.have(ID))
            throw new IllegalArgumentException("ID not found.");
        if (!Confirmation.deploy("Confirm deleting Loan with ID " + ID + "?")) return;
        loanMan.delete(ID);
        System.out.println();
        UIComponents.loadingBarWithColor("Updating the database...",
                20, 100, "BRIGHT_YELLOW");
        System.out.println(Input.successMessage("Successfully updated the database, Here's the new state:"));
        getActiveTable(loanMan);
        Input.enterToContinue();
    }

    public static void getActiveTable(LoanBank loanMan) {
        int count = 1;
        for (Loan loan : loanMan.getActive()) {
            System.out.println(loan.toString());
            int[] sizes = {7, 7, 7, 15, 15 , 5};
            String[] colors = {"BRIGHT_BLUE", "BRIGHT_YELLOW", "BRIGHT_RED", "", "", "BOOL"};
            FormattedTable ft = new FormattedTable(sizes, colors);
            System.out.print("| " + count + " ");
            ft.printWithColor(loan.toString(), ",");
            count++;
        }
    }

    public static void update(LoanBank loanMan) {
        String ID = Input.getString("Enter Transaction's ID");
        //check if exist
        if (!loanMan.have(ID.toUpperCase()))
            throw new IllegalArgumentException("Invalid ID");
        Loan data = loanMan.get(ID.toUpperCase());
        if (!data.isActive()) throw new IllegalStateException("You cannot active disabled Loan");
        while (true) {
            try {
                switch (LoanUpdateRouter.deploy(ID)) {
                    case 1:
                        LoanUpdateRouter.extendOneWeek(data);
                        break;
                    case 2:
                        LoanUpdateRouter.extendManyDays(data);
                        break;
                    case 3:
                        LoanUpdateRouter.adjustActive(data);
                        break;
                    case 4:
                        return;
                }
            } catch (Exception e) {
                Input.printError(e.getMessage());
            }
        }
    }

    public static void showAllLoan(LoanBank loanMan) {
        if (loanMan.isEmpty()) throw
                new IllegalStateException("No Loan data found");
        UIComponents.loadingBarWithColor("Fetching the database...",
                15, 50, "BRIGHT_YELLOW");

        Input.printSuccess("Currently Borrowed Books:");
        int count = 1;
        int[] sizes = {7, 7, 7, 10, 10, 5};
        String[] color2 = {"MAGENTA", "MAGENTA", "MAGENTA","MAGENTA","MAGENTA","MAGENTA"};
        FormattedTable ft = new FormattedTable(sizes, color2);
        System.out.print("| No ");
        ft.printWithColor("TranID, UserID,BookID, BorrowDate,ReturnDate,act",",");
        String[] colors = {"BRIGHT_BLUE", "CYAN", "", "", "", "BOOL"};
        ft = new FormattedTable(sizes,colors);
        for (Loan loan: loanMan.getAll()) {
            System.out.printf("| %2d " ,count);
            ft.printWithColor( loan.toString(),",");
            count++;
        }
        Input.enterToContinue();
    }
}
