package controller.book;

import models.Book;

public interface IBookLibrary {
    void add(Book book);

    void update(String id, Book book);

    void delete(String id);

    void parseFromString(String str);

    boolean have(String id);

    void commit();
}
