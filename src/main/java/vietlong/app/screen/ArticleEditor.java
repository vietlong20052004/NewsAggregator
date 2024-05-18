package vietlong.app.screen;

import vietlong.app.article.Article;
import vietlong.app.article.JsonArticleWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class ArticleEditor extends ArticleAdder {
    private Article articleToEdit;

    public ArticleEditor(Article article) {
        super();
        setTitle("Edit Article");
        articleToEdit = article;

        loadArticleDetails();
        initializeComponents();
    }

    @Override
    public void initializeComponents(){
        super.initializeComponents();
        for (ActionListener al : saveButton.getActionListeners()) {
            saveButton.removeActionListener(al);
        }
        saveButton.addActionListener(new SaveButtonListener());
    }

    private void loadArticleDetails() {
        urlField.setText(articleToEdit.getUrl());
        titleField.setText(articleToEdit.getTitle());
        dateField.setText(articleToEdit.getFormattedCreationDate());
        authorField.setText(String.join(",", articleToEdit.getAuthor()));
        hashtagsField.setText(String.join(",", articleToEdit.getHashtags()));
        categoryField.setText(articleToEdit.getCategory() != null ? String.join(",", articleToEdit.getCategory()) : "");
        contentTextArea.setText(articleToEdit.getContent());
    }


    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            articleToEdit.setUrl(urlField.getText());
            articleToEdit.setTitle(titleField.getText());
            try {
                articleToEdit.setCreationDate(dateField.getText());
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            articleToEdit.setAuthor(List.of(authorField.getText().split(",")));
            articleToEdit.setHashtags(List.of(hashtagsField.getText().split(",")));
            articleToEdit.setCategory(List.of(categoryField.getText().split(",")));
            articleToEdit.setContent(contentTextArea.getText());

            try {
                // Read existing articles from the file
                List<Article> articles = JsonArticleWriter.readFromFile("Data", "data_full.json");

                // Find the index of the article to edit
                int index = articles.indexOf(articleToEdit);
                if (index != -1) {
                    // Update the article in the list
                    articles.set(index, articleToEdit);

                    // Write updated list back to the file
                    JsonArticleWriter.writeToFile(articles, "Data", "data_full.json");

                    JOptionPane.showMessageDialog(ArticleEditor.this, "Article edited successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(ArticleEditor.this, "Article not found in the list.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ArticleEditor.this, "Failed to edit article: " + ex.getMessage());
            }
        }
    }
}

