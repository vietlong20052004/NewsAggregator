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
    private Map<String, User> users;
    private ArticleChooser articleChooser;
    private boolean editMode;

    public MainApplication(Map<String, User> users){

        setTitle("Main Application");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setFullScreen();


        // Set up layout
        appLayout = new CardLayout();
        mainPanel = new JPanel(appLayout);
        setContentPane(mainPanel);

        // Add login screen
        showLoginScreen();
        setVisible(true);

        // Get users
        this.users = users;
    }

    public void showArticleChooser(User user){
        mainPanel.removeAll();
        this.articleChooser = new ArticleChooser(this, user);
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
            addArticleMenuItem.addActionListener(e -> new ArticleAdder(this, user).setVisible(true));
            menu.add(addArticleMenuItem);
            JMenuItem editModeMenuItem = new JMenuItem("Edit Mode");
            this.editMode = false;

            editModeMenuItem.addActionListener(e -> {
                if (!articleChooser.isEditMode()) {
                    editModeMenuItem.setText("Exit Edit Mode");
                    articleChooser.toggleEditMode();
                    this.editMode = !this.editMode;
                    editModeMenuItem.revalidate();
                }  else {
                    editModeMenuItem.setText("Edit Mode");
                    articleChooser.toggleEditMode();
                    this.editMode = !this.editMode;
                    editModeMenuItem.revalidate();
                    }}
            );
            menu.add(editModeMenuItem);
            menu.revalidate();

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

    private void setFullScreen() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);  // Allow resizing

        // Adjust size and location
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        setSize(screenSize);
        setLocationRelativeTo(null);
    }


    public Map<String, User> getUsers(){
        return users;
    }


}


