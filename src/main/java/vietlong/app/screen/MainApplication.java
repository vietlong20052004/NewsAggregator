package vietlong.app.screen;

import vietlong.app.person.Author;
import vietlong.app.person.Edit;
import vietlong.app.person.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        users.put("longnv", new Author("longnv", "123",  "Long"));
        users.put("dungnt", new User("dungnt", "123", "Dung"));

        // Set up layout
        appLayout = new CardLayout();
        mainPanel = new JPanel(appLayout);
        setContentPane(mainPanel);

        // Add login screen
        showLoginScreen();
        setVisible(true);
    }

    public void showArticleChooser(User user){
        ArticleChooser articleChooser = new ArticleChooser(this, user);
        mainPanel.add(articleChooser, "ArticleChooser");
        appLayout.show(mainPanel,"ArticleChooser");
        setJMenuBar(createMenuBar(user));
        revalidate();
    }

    public void showLoginScreen() {
        // Remove all components from mainPanel
        mainPanel.removeAll();

        LoginScreen loginScreen = new LoginScreen(this);
        mainPanel.add(loginScreen,"LoginScreen");
        appLayout.show(mainPanel, "Login");
        setJMenuBar(null); // Remove when showing login screen
        revalidate(); // Ensure changes are going
    }

    private JMenuBar createMenuBar(User user) {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");

        // Add Article menu item
        if (user instanceof Edit) {
            JMenuItem addArticleMenuItem = new JMenuItem("Add Article");
            addArticleMenuItem.addActionListener(e -> new ArticleAdder().setVisible(true));
            menu.add(addArticleMenuItem);
        }

        // Logout menu item
        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginScreen();
            }
        });
        menu.add(logoutMenuItem);

        menuBar.add(menu);
        return menuBar;
    }


    public Map<String, User> getUsers(){
        return users;
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(()-> new MainApplication());
    }
}


