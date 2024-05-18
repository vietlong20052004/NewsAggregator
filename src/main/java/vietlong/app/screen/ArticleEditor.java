package vietlong.app.screen;

import vietlong.app.article.Article;
import vietlong.app.article.JsonArticleWriter;
import vietlong.app.person.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class ArticleEditor extends ArticleAdder {
    private Article articleToEdit;

    public ArticleEditor(Article article, MainApplication mainApplication, User user) {
        super(mainApplication, user);
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
        saveButton.addActionListener(saveButtonListener);
    }

    private void loadArticleDetails() {
        urlField.setText(articleToEdit.getUrl());
        titleField.setText(articleToEdit.getTitle());
        dateField.setText(articleToEdit.getPublishedDate().toString());
        authorField.setText(String.join(",", articleToEdit.getAuthor()));
        hashtagsField.setText(String.join(",", articleToEdit.getHashtags()));
        typeField.setText(articleToEdit.getCategory() != null ? String.join(",", articleToEdit.getCategory()) : "");
        contentTextArea.setText(articleToEdit.getContent());
    }



}

