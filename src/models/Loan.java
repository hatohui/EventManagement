package models;

import java.time.LocalDate;

public class Loan {
    private String tranID;
    private String userID;
    private String bookID;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private int extendedTimes;
    private int maxExtendTimes;
    private boolean active;

    public Loan(String tranID, String userID, String bookID, LocalDate borrowDate, LocalDate returnDate, Boolean active) {
        this.tranID = tranID;
        this.userID = userID;
        this.bookID = bookID;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.extendedTimes = 0;
        this.maxExtendTimes = 2;
        this.active = active;
    }

    public String getTranID() {
        return tranID;
    }

    public String getUserID() {
        return userID;
    }

    public String getBookID() {
        return bookID;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s",
                tranID, userID, bookID,
                borrowDate,returnDate,active);
    }

    public void setMaxExtendTimes(int times) {
        this.maxExtendTimes = times;
    }

    public void extendOneWeek() throws IllegalAccessException {
        if (extendedTimes > 2)
            throw new IllegalAccessException("User have already extended" +
                    " by " + maxExtendTimes + "times");
        this.returnDate = returnDate.plusWeeks(1);
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive() {
        this.active = true;
    }

    public void setInactive() {
        this.active = false;
    }

    public void extendByDays(long days) {
        this.returnDate = this.returnDate.plusDays(days);
    }
}


