package vietlong.app.screen;

import vietlong.app.article.Article;
import vietlong.app.search_engine.SearchTool;
import vietlong.app.search_engine.SortArticleByDate;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SearchBar extends JPanel{
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> sortComboBox;
    private SearchTool searchTool;
    private List<Article> articles;
    private ArticleChooser articleChooser;
    private List<JCheckBox> criteriaCheckboxes;
    private List<Article> searchResults;
    public SearchBar(ArticleChooser articleChooser, List<Article> articles) {
        this.articleChooser = articleChooser;
        this.articles = articles;

        initializeComponents();
    }

    private void initializeComponents() {
        this.searchTool = new SearchTool();
        criteriaCheckboxes = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add search image
        ImageIcon searchIcon = new ImageIcon("ImageIcon/search.png");
        JLabel searchLabel = new JLabel(searchIcon);
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(searchLabel, gbc);

        searchField = new JTextField();
        gbc.gridwidth = 12;
        gbc.gridx = 4;
        gbc.gridy = 0;
        panel.add(searchField, gbc);

        String[] criteriaLabels = {"Title", "Author", "Hashtag", "Content"};
        gbc.gridx = 0;
        for (String label : criteriaLabels) {
            JCheckBox checkBox = new JCheckBox(label);
            criteriaCheckboxes.add(checkBox);
            gbc.gridwidth = 2;
            gbc.gridx += 2;
            gbc.gridy = 1;
            panel.add(checkBox, gbc);
        }

        JLabel sortLabel = new JLabel("Sort by:");
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(sortLabel, gbc);

        sortComboBox = new JComboBox<>(new String[]{"None", "Newest", "Oldest"}); // Default "None"
        gbc.gridwidth = 6;
        gbc.gridx = 5;
        gbc.gridy = 2;
        panel.add(sortComboBox, gbc);

        this.searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 3;

        panel.add(searchButton, gbc);

        add(panel);


    }

    private void performSearch() {
        String keyword = searchField.getText().trim();
        List<String> criteriaList = new ArrayList<>();
        for (JCheckBox checkBox : criteriaCheckboxes) {
            if (checkBox.isSelected()) {
                criteriaList.add(checkBox.getText().toLowerCase());
            }
        }

        if (!keyword.isEmpty()) {
            searchResults = searchTool.search(articles, keyword, criteriaList);
            String sortOption = (String) sortComboBox.getSelectedItem();
            assert sortOption != null;
            if (sortOption.equals("Newest")){
                SortArticleByDate.sortByTime(searchResults, true);
            } else if (sortOption.equals("Oldest")){
                SortArticleByDate.sortByTime(searchResults, false);
            }
            articleChooser.displaySearchResults(searchResults);
        } else {

            searchResults = new ArrayList<> (Objects.requireNonNull(ArticleChooser.loadArticles()));
            String sortOption = (String) sortComboBox.getSelectedItem();
            assert sortOption != null;
            if (sortOption.equals("Newest")){
                SortArticleByDate.sortByTime(searchResults, true);
            } else if (sortOption.equals("Oldest")){
                SortArticleByDate.sortByTime(searchResults, false);
            }
            articleChooser.displaySearchResults(searchResults);
        }
    }


}
