package controller.loan;

import controller.book.BookLibrary;
import controller.user.UserBank;
import models.Book;
import models.Loan;
import models.User;
import utils.FileIO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class LoanBank extends HashMap<String, Loan> implements ILoanBank {
    private final UserBank USER_BANK;
    private final BookLibrary BOOK_LIBRARY;
    private static int defaultRentDays = 14;

    private static final FileIO IO = new FileIO("src/data/Loans.txt");

    public static void setDefaultRentDays(int days) {
        defaultRentDays = days;
    }

    public LoanBank(BookLibrary bookMan, UserBank userMan) {
        USER_BANK = userMan;
        BOOK_LIBRARY = bookMan;
        IO.getState().forEach(this::parseFromString);
    }

    @Override
    public void add(Loan loan) {
        this.put(loan.getTranID(), loan);
        this.BOOK_LIBRARY.delete(loan.getBookID());
    }

    @Override
    public void update(String id, Loan data) {
        this.put(id.trim(), data);
    }

    @Override
    public void delete(String id) {
        this.get(id.trim()).setInactive();
        this.BOOK_LIBRARY.setActive(this.get(id).getBookID());
    }

    @Override
    public void parseFromString(String str) {
        if (str.isEmpty())
            throw new IllegalArgumentException("Empty data");
        String[] data = str.trim().split(",");
        if (data.length != 6)
            throw new IllegalStateException("Insufficient data");

        Loan newBook = new Loan(data[0], data[1], data[2],
                LocalDate.parse(data[3]), LocalDate.parse(data[4].trim()),
                Boolean.parseBoolean(data[5])
        );
        this.add(newBook);
    }

    @Override
    public boolean have(String id) {
        return this.containsKey(id);
    }

    @Override
    public void commit() {
        IO.clear();
        this.values().forEach(obj -> IO.append(obj.toString()));
        IO.commit();
    }

    //get overdue loans
    public Collection<Loan> getOverdue(LocalDate returnDate) {
        Collection<Loan> overdueLoans = new ArrayList<>();
        this.values().forEach(loan -> {
            if (loan.getReturnDate().isBefore(returnDate) && loan.isActive()) {
                overdueLoans.add(loan);
            }
        });
        return overdueLoans;
    }

    public HashMap<String,Book> getBorrowedBooks() {
        HashMap<String,Book> borrowed = new HashMap<>();
        this.values().forEach(loan -> {
            borrowed.put(loan.getUserID(),
                    BOOK_LIBRARY.get(loan.getBookID()));
        });
        return borrowed;
    }

    public boolean haveUser(String ID) {
        return this.USER_BANK.have(ID.trim());
    }

    public boolean haveBook(String userID) {
        return this.BOOK_LIBRARY.have(userID.trim());
    }

    public int getDefaultRentDays() {
        return defaultRentDays;
    }

    public User getUser(String ID) {
        return this.USER_BANK.get(ID);
    }

    public ArrayList<Loan> getActive() {
        ArrayList<Loan> loans = new ArrayList<>();
        this.values().forEach(loan -> {
            if (loan.isActive()) loans.add(loan);
        });
        return loans;
    }

    public Book getBook(String ID) {
        return BOOK_LIBRARY.get(ID);
    }

    public ArrayList<Loan> getAllByPeriod(LocalDate after, LocalDate before) {
        ArrayList<Loan> loans = new ArrayList<>();
        this.values().forEach(loan -> {
            if (loan.getBorrowDate().isAfter(after)
                    && loan.getBorrowDate().isBefore(before)) {
                loans.add(loan);
            }
        });
        return loans;
    }

    public ArrayList<Loan> getAll() {
        return new ArrayList<>(this.values());
    }

    public void extendOneWeek(String ID) throws IllegalAccessException {
        this.get(ID).extendOneWeek();
    }

    public void extendByDays(String ID, int days) {
        this.get(ID).extendByDays(days);
    }
}
