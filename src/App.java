import controller.book.BookLibrary;
import controller.loan.LoanBank;
import controller.user.UserBank;
import utils.Input;
import views.MenuRouter;
import views.BookRouter;
import components.SaveData;
import views.LoanRouter;
import views.UserRouter;
import views.ReportRouter;

public class App {
    private static final BookLibrary bookMan = new BookLibrary();
    private static final UserBank userMan = new UserBank();
    private static final LoanBank loanMan = new LoanBank(bookMan, userMan);

    public static void run() {
        while (true) {
            switch (MenuRouter.deploy()) {
                case 1:
                    manageLoan();
                    break;
                case 2:
                    manageBook();
                    break;
                case 3:
                    manageUser();
                    break;
                case 4:
                    report();
                    break;
                case 5:
                    SaveData.save(bookMan,userMan,loanMan);
                    break;
                case 6:
                    Input.printSuccess("Exiting App...");
                    return;
            }
        }
    }

    private static void report() {
        while (true) {
            try {
                switch (ReportRouter.deploy()) {
                    case 1:
                        ReportRouter.showAllBorrowedBook(loanMan);
                        break;
                    case 2:
                        ReportRouter.showOverDueBooks(loanMan);
                        break;
                    case 3:
                        ReportRouter.showActivities(loanMan);
                        break;
                    case 4:
                        return;
                }
            } catch (Exception e) {
                Input.printError(e.getMessage());
                Input.enterToContinue();
            }
        }
    }

    private static void manageUser() {
        while (true) {
            try {
                switch (UserRouter.deploy()) {
                    case 1:
                        UserRouter.add(userMan);
                        break;
                    case 2:
                        UserRouter.update(userMan);
                        break;
                    case 3:
                        UserRouter.delete(userMan);
                        break;
                    case 4:
                        UserRouter.showAll(userMan);
                    case 5:
                        return;
                }
            } catch (Exception e) {
                Input.printError(e.getMessage());
                Input.enterToContinue();
            }
        }
    }

    private static void manageLoan() {
        while (true) {
            try {
                switch (LoanRouter.deploy()) {
                    case 1:
                        LoanRouter.add(loanMan);
                        break;
                    case 2:
                        LoanRouter.returnABook(loanMan);
                        break;
                    case 3:
                        LoanRouter.update(loanMan);
                        break;
                    case 4:
                        LoanRouter.showAllLoan(loanMan);
                        break;
                    case 5:
                        return;
                }
            } catch (Exception e) {
                Input.printError(e.getMessage());
                Input.enterToContinue();
            }
        }
    }

    private static void manageBook() {
        while (true) {
            try {
                switch (BookRouter.deploy()) {
                    case 1:
                        BookRouter.add(bookMan);
                        break;
                    case 2:
                        BookRouter.update(bookMan);
                        break;
                    case 3:
                        BookRouter.delete(bookMan);
                        break;
                    case 4:
                        BookRouter.showAll(bookMan);
                        break;
                    case 5:
                        BookRouter.sortByPublicYear(bookMan);
                    case 6:
                        return;
                }
            } catch (Exception e) {
                Input.printError(e.getMessage());
                Input.enterToContinue();
            }
        }
    }
}
