package vietlong.app.screen;

import vietlong.app.article.Article;
import vietlong.app.article.JsonArticleWriter;
import vietlong.app.person.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArticleEditor extends ArticleAdder {
    private Article articleToEdit;
    private List<Article> articles;
    private JButton deleteButton;

    public ArticleEditor( MainApplication mainApplication, User user, List<Article> articles,Article article) {
        super(mainApplication, user);
        setTitle("Edit Article");
        articleToEdit = article;
        this.articles = articles;
        initializeComponents();
        loadArticleDetails();
    }

    @Override
    public void initializeComponents(){
        super.initializeComponents();
        for (ActionListener a: saveButton.getActionListeners()){
            saveButton.removeActionListener(a);
        }
        saveButton.addActionListener(new SaveButtonListener());

        this.deleteButton = new JButton("Delete Article");
        deleteButton.addActionListener(new DeleteButtonListener());
        mainPanel.add(deleteButton);
    }

    private void loadArticleDetails() {
        urlField.setText(articleToEdit.getUrl());
        titleField.setText(articleToEdit.getTitle());
        dateField.setText(articleToEdit.convertToFormattedDate());
        authorField.setText(String.join(",", articleToEdit.getAuthor()));
        hashtagsField.setText(String.join(",", articleToEdit.getHashtags()));
        typeField.setSelectedItem(articleToEdit.getArticleType() != null ? articleToEdit.getArticleType() : "");
        contentTextArea.setText(articleToEdit.getContent());
    }
    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            articles = new ArrayList<> (Objects.requireNonNull(ArticleChooser.loadArticles()));
            articles.remove(articleToEdit); // remove old article
            articleToEdit.setUrl(urlField.getText().trim());
            articleToEdit.setTitle(titleField.getText().trim());
            try {
                articleToEdit.setArticleType(Article.ArticleType.fromString((String)typeField.getSelectedItem()));
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(ArticleEditor.this, "Invalid article type: " + typeField.getSelectedItem());
                return;
            }
            try {
                articleToEdit.setPublishedDate(dateField.getText());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(ArticleEditor.this, "Invalid date format. Please use yyyy-MM-dd.");
                return;
            }
            articleToEdit.setAuthor(List.of(authorField.getText().trim().split("\\s*,\\s*")));
            articleToEdit.setHashtags(List.of(hashtagsField.getText().trim().split("\\s*,\\s*")));
            articleToEdit.setContent(contentTextArea.getText().trim());

            try {

                articles.add(0, articleToEdit); // add new article at 0 index
                // Write updated list back to the file
                JsonArticleWriter.writeToFile(articles, "Data", "data_full.json");
                JOptionPane.showMessageDialog(ArticleEditor.this, "Article saved successfully!");

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ArticleEditor.this, "Failed to save article: " + ex.getMessage());
            }

            mainApplication.showArticleChooser(user);
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                articles = new ArrayList<>(articles);
                articles.remove(articleToEdit);
                JsonArticleWriter.writeToFile(articles, "Data", "data_full.json");
                JOptionPane.showMessageDialog(ArticleEditor.this, "Article deleted successfully!");
                deleteButton.setEnabled(false);
                dispose();

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ArticleEditor.this, "Failed to delete article: " + ex.getMessage());
            }

            mainApplication.showArticleChooser(user);
        }
    }


}

