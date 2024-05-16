package vietlong.app.screen;

import vietlong.app.person.User;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainApplication extends JFrame{
    private final CardLayout appLayout;
    private final JPanel mainPanel;
    private final Map<String, User> users;

    public MainApplication(){
        setTitle("Main Application");
        setSize(800, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize users
        users = new HashMap<>();
        users.put("longnv", new User("longnv", "123", true, "Long"));
        users.put("dungnt", new User("dungnt", "123", false, "Dung"));

        // Set up layout
        appLayout = new CardLayout();
        mainPanel = new JPanel(appLayout);
        setContentPane(mainPanel);

        // Add login screen
        LoginScreen loginScreen = new LoginScreen(this);
        mainPanel.add(loginScreen, "LoginScreen");
        setVisible(true);
    }

    public void showArticleChooser(User user){
        ArticleChooser articleChooser = new ArticleChooser(user);
        mainPanel.add(articleChooser, "ArticleChooser");
        appLayout.show(mainPanel,"ArticleChooser");

    }
    public Map<String, User> getUsers(){
        return users;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(()-> new MainApplication());
    }
}


