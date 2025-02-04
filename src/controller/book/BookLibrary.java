package controller.book;

import models.Book;
import utils.FileIO;

import java.util.*;

public class BookLibrary extends HashMap<String, Book> implements IBookLibrary {
    //init
    private static final FileIO IO = new FileIO("src/data/Books.txt");

    {
        IO.getState().forEach(this::parseFromString);
    }

    // Add a book into the list
    @Override
    public void add(Book book) {
        this.put(book.getID(), book);
    }

    // Update the content inside a book
    @Override
    public void update(String ID, Book book) {
        this.put(ID, book);
    }

    // Set a book status as inactive
    @Override
    public void delete(String ID) {
        if (this.containsKey(ID))
            this.get(ID).setInactive();
        else throw new IllegalArgumentException("Invalid ID");
    }

    //save info into file
    public void commit() {
        IO.clear();
        this.values().forEach(obj -> IO.append(obj.toString()));
        IO.commit();
    }

    //get active books
    public ArrayList<Book> getActive() {
        ArrayList<Book> activeBooks = new ArrayList<>();
        for (Book book : this.values()) {
            if (book.isActive()) {
                activeBooks.add(book);
            }
        }
        return activeBooks;
    }

    public Collection<Book> getAllSortedByID() {
        ArrayList<Book> books = new ArrayList<>(this.values());
        books.sort(Comparator.comparing(Book::getID));
        return books;
    }

    //return a collection of all Books in the bank
    public Collection<Book> getAll() {
        return this.values();
    }

    /**
     * DATA PROCESSING
     */

    //Converting valid string into Book type.
    @Override
    public void parseFromString(String s) {
        if (s.isEmpty())
            throw new IllegalArgumentException("Empty data");
        String[] data = s.trim().split(",");
        if (data.length != 7)
            throw new IllegalStateException("Insufficient data");

        Book newBook = new Book(data[0], data[1], data[2],
                Integer.parseInt(data[3]), data[4], data[5],
                Boolean.parseBoolean(data[6].trim()));
        this.add(newBook);
    }

    //check if library have book with this id
    @Override
    public boolean have(String id) {
        return this.containsKey(id);
    }

    public void setActive(String bookID) {
        this.get(bookID).setActive();
    }

    public ArrayList<Book> getByPeriod(int yearBefore, int yearAfter) {
        ArrayList<Book> sorted = new ArrayList<>();
        this.values().forEach(each -> {
            if (each.getPubicYear() >= yearBefore && each.getPubicYear() <= yearAfter) {
                sorted.add(each);
            }
        });
        return sorted;
    }

    public ArrayList<Book> getSortedByPublicYear(int yearBefore, int yearAfter) {
        ArrayList<Book> sorted = new ArrayList<>(getByPeriod(yearBefore, yearAfter));
        Collections.sort(sorted, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o2.getPubicYear() - o1.getPubicYear();
            }
        });
        return sorted;
    }
}
