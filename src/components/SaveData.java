package components;

import controller.book.BookLibrary;
import controller.loan.LoanBank;
import controller.user.UserBank;
import utils.Input;
import utils.UIComponents;

public class SaveData {
    public static void save(BookLibrary bookMan, UserBank userMan, LoanBank loanMan) {
        try {
            bookMan.commit();
            userMan.commit();
            loanMan.commit();
            UIComponents.loadingBarWithColor("Saving Data...",
                    40, 50, "BRIGHT_GREEN");
            System.out.print(Input.successMessage("Data saved successfully!"));
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
