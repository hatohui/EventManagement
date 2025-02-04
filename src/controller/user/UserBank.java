package controller.user;

import models.Book;
import models.User;
import utils.FileIO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

public class UserBank extends HashMap<String, User> implements IUserBank {
    private static final FileIO IO = new FileIO("src/data/Users.txt");

    {
        IO.getState().forEach(this::parseFromString);
    }

    @Override
    public void add(User user) {
        this.put(user.getID(), user);
    }

    @Override
    public void update(String id, User data) {
        this.put(id, data);
    }

    @Override
    public void delete(String id) {
        this.get(id).setInactive();
    }

    @Override
    public void parseFromString(String str) {
        if (str.isEmpty())
            throw new IllegalArgumentException("Empty data");
        String[] data = str.split(",");
        if (data.length != 6)
            throw new IllegalStateException("Insufficient data");

        User newUser = new User(data[0], data[1],
                LocalDate.parse(data[2]), data[3], data[4],
                Boolean.parseBoolean(data[5].strip()));
        this.add(newUser);
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

    public ArrayList<User> getActive() {
        ArrayList<User> activeUsers = new ArrayList<>();
        for (User user : this.values()) {
            if (user.isActive()) {
                activeUsers.add(user);
            }
        }
        return activeUsers;
    }

    public Collection<User> getAllSortedByID() {
        ArrayList<User> books = new ArrayList<>(this.values());
        books.sort(Comparator.comparing(User::getID));
        return books;
    }

    public Collection<User> getAll() {
        return this.values();
    }
}
