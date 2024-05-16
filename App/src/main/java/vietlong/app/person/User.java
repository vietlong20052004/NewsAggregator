package vietlong.app.person;

public class User {
    private final String username;
    private final String password;
    private final boolean isAdmin;
    private final String name;

    public User(String username, String password, boolean isAdmin, String name) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
