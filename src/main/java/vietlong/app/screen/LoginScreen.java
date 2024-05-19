package vietlong.app.screen;

import vietlong.app.person.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.HashMap;

public class LoginScreen extends JPanel{
    private final BackgroundPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final MainApplication mainApp;
    private ImageIcon loginImage;

    public LoginScreen(MainApplication mainApp){
        this.mainPanel = new BackgroundPanel("ImageIcon/login_background.jpg");
        this.mainApp = mainApp;
        mainApp.add(mainPanel);
        initializeComponents();

    }

    private void initializeComponents(){
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components

        // Set font
        Font labelFont = new Font("SansSerif", Font.BOLD, 16);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 14);

        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(usernameLabel, gbc);

        // Username Field
        usernameField = new JTextField(15);
        usernameField.setFont(fieldFont);
        usernameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(usernameField, gbc);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(passwordLabel, gbc);

        // Password Field
        passwordField = new JPasswordField(15);
        passwordField.setFont(fieldFont);
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(passwordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(buttonFont);
        loginButton.setPreferredSize(new Dimension(100, 40));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton.addActionListener(new LoginButtonListener());
        mainPanel.add(loginButton, gbc);
    }



    private class LoginButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = mainApp.getUsers().get(username);

            if (user != null && user.getPassword().equals(password)){
                mainApp.showArticleChooser(user);
            } else {
                JOptionPane.showMessageDialog(LoginScreen.this,"Invalid username or password" );
            }

        }
    }
}
