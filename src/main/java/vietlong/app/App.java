package vietlong.app;

import vietlong.app.person.Author;
import vietlong.app.person.User;
import vietlong.app.screen.MainApplication;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        // Initialize users
        Map<String, User> users = new HashMap<>();
        users.put("admin", new Author("admin", "1",  "Admin 1"));
        users.put("user", new User("user", "1", "User 1"));

        SwingUtilities.invokeLater(()-> new MainApplication(users));
    }
}
