package models;

public class Book {
    private final String ID;
    private String title;
    private String author;
    private int pubicYear;
    private String publisher;
    private String ISBN;
    private boolean active;

    public Book(String ID, String title, String author, int pubicYear, String publisher, String ISBN, boolean active) {
        this.ID = ID;
        this.title = title;
        this.author = author;
        this.pubicYear = pubicYear;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.active = active;
    }

    public String getID() {
        return ID;
    }

    public void setActive() {
        this.active = true;
    }

    public void setInactive() {
        this.active = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPubicYear() {
        return pubicYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPubicYear(int pubicYear) {
        this.pubicYear = pubicYear;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String toString() {
        return String.format("%s,%s,%s,%d,%s,%s,%s\n",
                ID, title, author, pubicYear, publisher, ISBN, active);
    }
}
