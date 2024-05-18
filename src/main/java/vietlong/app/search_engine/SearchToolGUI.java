package vietlong.app.search_engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import vietlong.app.search_engine.SearchTool;
import vietlong.app.search_engine.SortArticleByDate;
import vietlong.app.article.Article;
import java.io.IOException;
import java.text.ParseException;

import static vietlong.app.article.JsonArticleReader.readFromFile;

public class SearchToolGUI extends JFrame {
    private JTextField keywordField;
    private JTextArea resultArea;
    private JComboBox<String> sortComboBox;
    private SearchTool searchTool;
    private List<Article> articles;
    private List<JCheckBox> criteriaCheckboxes;

    public SearchToolGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Article Search Tool");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        searchTool = new SearchTool();
        criteriaCheckboxes = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 1)); // 0 row, 1 column

        JLabel keywordLabel = new JLabel("Keyword:");
        keywordField = new JTextField();
        inputPanel.add(keywordLabel);
        inputPanel.add(keywordField);

        // Add checkboxes for criteria
        String[] criteriaLabels = {"Title", "Author", "Hashtag", "Content"};
        for (String label : criteriaLabels) {
            JCheckBox checkBox = new JCheckBox(label);
            criteriaCheckboxes.add(checkBox);
            inputPanel.add(checkBox);
        }

        // Initialize JComboBox for sorting with "None" as default
        JLabel sortLabel = new JLabel("Sort by:");
        inputPanel.add(sortLabel);

        sortComboBox = new JComboBox<>(new String[]{"None", "Newest", "Oldest"}); // Default "None"
        inputPanel.add(sortComboBox);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        inputPanel.add(searchButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(resultArea);

        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    private void search() {
        String keyword = keywordField.getText();
        List<String> criteriaList = new ArrayList<>();
        for (JCheckBox checkBox : criteriaCheckboxes) {
            if (checkBox.isSelected()) {
                criteriaList.add(checkBox.getText().toLowerCase());
            }
        }
        if (articles == null) {
            resultArea.setText("No articles loaded.");
            return;
        }
        List<Article> searchResult = searchTool.search(articles, keyword, criteriaList);
        if (searchResult.isEmpty()) {
            resultArea.setText("No articles found for the given keyword and criteria.");
        } else {
            // Sort results based on the selected sort option
            String sortOption = (String) sortComboBox.getSelectedItem();
            if (sortOption.equals("Newest")) {
                SortArticleByDate.sortByTime(searchResult, true);
            } else if (sortOption.equals("Oldest")) {
                SortArticleByDate.sortByTime(searchResult, false);
            }
            displayResults(searchResult); // Display sorted results
        }
    }

    private void displayResults(List<Article> searchResult) {
        resultArea.setText("Search results:\n");
        for (Article article : searchResult) {
            resultArea.append(article.toString() + "\n");
        }
    }

    public static void main(String[] args) throws ParseException, IOException {
        List<Article> articles = readFromFile("Data", "data_full.json");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SearchToolGUI gui = new SearchToolGUI();
                gui.setArticles(articles);
                gui.setVisible(true);
            }
        });
    }
}