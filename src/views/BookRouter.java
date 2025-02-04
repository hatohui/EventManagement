package views;

import components.FormattedTable;
import components.Confirmation;
import controller.book.BookLibrary;
import models.Book;
import utils.*;

public class BookRouter {
    private static final UserInterface UI;
    private static final String TITLE = "LIBRARY MANAGEMENT SYSTEM";
    private static final String[] OPTIONS = {"  Add   ", "Update", "Remove", "Show all", "Show sorted by public year", "Return"};

    static {
        UI = new UIBuilder(75)
                .topWall()
                .rightString(TITLE, 5)
                .emptyWall()
                .header("== MANAGE BOOKS ==").withColor("BRIGHT_YELLOW","BLACK")
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
    public static void add(BookLibrary bl) {
        do {
            String ID = "";
            do {
                ID = IntegerID.getWithPrefix("B");
            } while (bl.have(ID));
            String title = Input.getString("Input new book's title", true);
            String author = Input.getString("Input new book's author");
            int publicYear = Input.getInteger("Input the book's publication year", 1000, 3000);
            String publisher = Input.getString("Input the book's publisher", true);
            String ISBN = Input.getString("Input the book's ISBN");
            bl.add(new Book(ID, title, author, publicYear, publisher, ISBN, true));
        } while (Confirmation.deploy("Add another book?"));
    }

    public static void delete(BookLibrary bookMan) {
        String ID;
        while (true) {
            try {
                ID = Input.getStringNoConstraint("Book ID");
                if (!bookMan.have(ID.trim().toUpperCase())) throw new IllegalArgumentException("Invalid ID.");
                break;
            } catch (Exception e) {
                Input.printError(e.getMessage());
                Input.enterToContinue();
            }
        }
        bookMan.delete(ID.trim());
        if (!Confirmation.deploy("Confirm deleting User with ID " + ID + "?")) return;
        System.out.println();
        UIComponents.loadingBarWithColor("Updating the database...",
                20, 100, "BRIGHT_YELLOW");
        System.out.println(Input.successMessage("Successfully updated the database, Here's the new state:"));
        getActiveTable(bookMan);
        Input.enterToContinue();
    }

    public static void update(BookLibrary bookMan) {
        if (bookMan.isEmpty()) throw new IllegalStateException("No book data found.");
        String ID = Input.getString("Enter Book's ID");
        if (!bookMan.have(ID.toUpperCase()))
            throw new IllegalArgumentException("Invalid ID");
        Book preFix = bookMan.get(ID);
        Book data = new Book(preFix.getID(),
                preFix.getTitle(),
                preFix.getAuthor(),
                preFix.getPubicYear(),
                preFix.getPublisher(),
                preFix.getAuthor(),
                preFix.isActive()
        );
        while (true) {
            try {
                switch (BookUpdateRouter.deploy(ID)) {
                    case 1:
                        BookUpdateRouter.adjustTitle(data);
                        break;
                    case 2:
                        BookUpdateRouter.adjustAuthor(data);
                        break;
                    case 3:
                        BookUpdateRouter.adjustPublicYear(data);
                        break;
                    case 4:
                        BookUpdateRouter.adjustPublisher(data);
                        break;
                    case 5:
                        BookUpdateRouter.adjustISBN(data);
                        break;
                    case 6:
                        BookUpdateRouter.adjustAvailability(data);
                        break;
                    case 7:
                        BookUpdateRouter.confirmChange(bookMan, data);
                        return;
                    case 8:
                        if (!BookUpdateRouter.antiAccidentLeave()) break;
                        BookUpdateRouter.resetChanges();
                        return;
                }
            } catch (Exception e) {
                Input.printError(e.getMessage());
            }
        }
    }

    public static void getActiveTable(BookLibrary bookMan) {
        int count = 1;
        int[] sizes = {7, 25, 15, 4, 15, 15 ,4};
        String[] colors = {"BRIGHT_BLUE", "", "", "", "", "", "BOOL"};
        FormattedTable ft = new FormattedTable(sizes, colors);
        for (Book book :bookMan.getActive()) {
            System.out.print("| " + count + " ");
            ft.printWithColor(book.toString(), ",");
            count++;
        };
    }

    public static void showAll(BookLibrary bookMan) {
        if (bookMan.isEmpty()) throw new IllegalStateException("No Book Found");
        UIComponents.loadingBarWithColor("Fetching the database...",
                15, 50, "BRIGHT_YELLOW");

        Input.printSuccess("Current Books:");
        int count = 1;
        int[] sizes = {7, 25, 15, 15, 15, 20 , 5};
        String[] color2 = {"MAGENTA", "MAGENTA", "MAGENTA","MAGENTA","MAGENTA","MAGENTA","MAGENTA"};
        FormattedTable ft = new FormattedTable(sizes, color2);
        System.out.print("| No ");
        ft.printWithColor("BookID,   Book Title, Author,PY,Publisher,   ISBN, act",",");
        String[] colors = {"BRIGHT_BLUE", "", "", "", "", "", "BOOL"};
        ft = new FormattedTable(sizes,colors);
        for (Book book: bookMan.getAllSortedByID()) {
            System.out.printf("| %2d " ,count);
            ft.printWithColor(book.toString(),",");
            count++;
        }
        Input.enterToContinue();
    }

    public static void sortByPublicYear(BookLibrary bookMan) {
        int after = Input.getInteger("Enter year x", 1000, 3000);
        int before =  Input.getInteger("Enter year y", 1000, 3000);
        UIComponents.loadingBarWithColor("Fetching the database...",
                15, 50, "BRIGHT_YELLOW");
        Input.printSuccess("Current Books sorted by public year:");
        int count = 1;
        int[] sizes = {7, 25, 15, 15, 15, 20 , 5};
        String[] color2 = {"MAGENTA", "MAGENTA", "MAGENTA","MAGENTA","MAGENTA","MAGENTA","MAGENTA"};
        FormattedTable ft = new FormattedTable(sizes, color2);
        System.out.print("| No ");
        ft.printWithColor("BookID,   Book Title, Author,PY,Publisher,   ISBN, act",",");
        String[] colors = {"BRIGHT_BLUE", "", "", "", "", "", "BOOL"};
        ft = new FormattedTable(sizes,colors);
        for (Book book: bookMan.getSortedByPublicYear(after, before)) {
            System.out.printf("| %2d " ,count);
            ft.printWithColor(book.toString(),",");
            count++;
        }
        Input.enterToContinue();
    }
}
