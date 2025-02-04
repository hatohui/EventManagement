package controller.user;

import models.User;

public interface IUserBank {
    void add(User user);

    void update(String id, User data);

    void delete(String id);

    void parseFromString(String str);

    boolean have(String id);

    void commit();
}
