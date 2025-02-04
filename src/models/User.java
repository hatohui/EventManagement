package models;

import java.time.LocalDate;

public class User {
    private final String ID;
    private String name;
    private LocalDate birthday;
    private String phoneNumber;
    private String email;
    private boolean active;

    public User(String ID, String name, LocalDate birthday, String phoneNumber, String email, boolean active) {
        this.ID = ID;
        this.name = name;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.active = active;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s\n",
                ID, name, birthday, phoneNumber, email, active);
    }

    public void setInactive() {
        this.active = false;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive() {
        this.active = true;
    }
}
