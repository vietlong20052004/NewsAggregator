package vietlong.app.screen;

import com.sun.tools.javac.Main;
import jdk.jfr.Category;
import vietlong.app.article.Article;
import vietlong.app.article.JsonArticleWriter;
import vietlong.app.person.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class ArticleAdder extends JFrame {

    protected  JPanel mainPanel;
    protected JTextField urlField;
    protected JTextField titleField;
    protected JTextField dateField;
    protected JTextField authorField;
    protected JTextField hashtagsField;
    protected JComboBox<String> typeField;

    protected JTextArea contentTextArea;
    protected JButton saveButton;
    protected SaveButtonListener saveButtonListener;
    protected MainApplication mainApplication;
    protected User user;

    public ArticleAdder(MainApplication mainApplication, User user) {
        this.mainApplication = mainApplication;
        this.user = user;
        setTitle("Add Article");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();

    }

    public void initializeComponents(){
        this.mainPanel = new BackgroundPanel("ImageIcon/background_2.jpg");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        urlField = new JTextField();
        titleField = new JTextField();
        dateField = new JTextField();
        authorField = new JTextField();
        hashtagsField = new JTextField();
        typeField = new JComboBox<>(new String[]{"Tweet", "News", "Blog_Post", "Facebook_Post"});

        contentTextArea = new JTextArea();
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setPreferredSize(new Dimension(700, 400));
        saveButtonListener = new SaveButtonListener();

        mainPanel.add(createLabeledField("URL: ", urlField, new Dimension(800,30), new Dimension(900,40)));
        mainPanel.add(createLabeledField("Title: ", titleField, new Dimension(800,30), new Dimension(900,40)));
        mainPanel.add(createLabeledField("Date: ", dateField, new Dimension(800,30), new Dimension(900,40)));
        mainPanel.add(createLabeledField("Author: ", authorField, new Dimension(800,30), new Dimension(900,40)));
        mainPanel.add(createLabeledField("Hashtags: ", hashtagsField, new Dimension(800,30), new Dimension(900,40)));
        mainPanel.add(createLabeledField("Type ", typeField, new Dimension(800,30), new Dimension(900,40)));
        mainPanel.add(createLabeledField("Content: ", new JScrollPane(contentTextArea), new Dimension(800,350), new Dimension(900,400)));

        this.saveButton = new JButton("Save Article");
        saveButton.addActionListener(saveButtonListener);
        mainPanel.add(saveButton);

        add(mainPanel);
    }

    private JPanel createLabeledField(String labelText, JComponent field,Dimension fieldSize, Dimension panelSize) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setOpaque(false);
        label.setPreferredSize(new Dimension(100, 30));
        field.setPreferredSize(fieldSize);
        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        panel.setMaximumSize(panelSize);
        panel.setOpaque(false);
        return panel;
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Article article = new Article();
            article.setUrl(urlField.getText().trim());
            article.setTitle(titleField.getText().trim());
            try {
                article.setArticleType(Article.ArticleType.fromString((String)typeField.getSelectedItem()));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(ArticleAdder.this, "Invalid article type: " + typeField.getSelectedItem());
                return;
            }
            try {
                article.setPublishedDate(dateField.getText());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(ArticleAdder.this, "Invalid date format. Please use yyyy-MM-dd.");
                return;
            }
            article.setAuthor(List.of(authorField.getText().trim().split("\\s*,\\s*")));
            article.setHashtags(List.of(hashtagsField.getText().trim().split("\\s*,\\s*")));
            article.setContent(contentTextArea.getText().trim());

            try {
                // Read existing articles from the file
                List<Article> articles = JsonArticleWriter.readFromFile("Data", "data_full.json");

                // Add new article to the list
                articles.add(0,article);

                // Write updated list back to the file
                JsonArticleWriter.writeToFile(articles, "Data", "data_full.json");
                JOptionPane.showMessageDialog(ArticleAdder.this, "Article saved successfully!");

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ArticleAdder.this, "Failed to save article: " + ex.getMessage());
            }

            mainApplication.showArticleChooser(user);
        }
    }
}
