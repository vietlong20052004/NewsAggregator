package vietlong.app.screen;


import vietlong.app.article.Article;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.net.*;
import java.text.ParseException;

import java.util.List;

import static vietlong.app.article.JsonArticleReader.readFromDirectory;


public class ArticleViewer extends JFrame {
    private JPanel mainPanel;
    private JLabel urlLabel;
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JLabel authorLabel;
    private JLabel hashtagsLabel;
    private JLabel categoryLabel;
    private JTextArea contentTextArea;

    public ArticleViewer(Article article){
        setTitle("Article Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents(article);
        displayArticle(article);

    }

    private void initializeComponents(Article article){
        mainPanel = new BackgroundPanel("ImageIcon/background.jpg");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        urlLabel = new JLabel();
        titleLabel = new JLabel();
        dateLabel = new JLabel();
        authorLabel = new JLabel();
        hashtagsLabel = new JLabel();
        categoryLabel = new JLabel();
        contentTextArea = new JTextArea();
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setEditable(false);

        mainPanel.add(urlLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(dateLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(authorLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(hashtagsLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(categoryLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(new JScrollPane(contentTextArea));
        add(mainPanel);
    }

    public void displayArticle(Article article) {
        urlLabel.setText("<html><a>" + article.getUrl() + "</a></html>");
        urlLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        urlLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(article.getUrl()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                urlLabel.setText("<html>URL: <a style=\"color: blue; text-decoration: underline;\" href=\"\">" + article.getUrl() + "</a></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                urlLabel.setText("URL: " + article.getUrl());
            }
        });

        urlLabel.setText("URL: " + article.getUrl());
        titleLabel.setText("Title: " + article.getTitle());
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Serif",Font.BOLD,25));
        dateLabel.setText("Date: " + article.getFormattedCreationDate());
        authorLabel.setText("Author: " + String.join(", ", article.getAuthor()));
        hashtagsLabel.setText("Hashtags: " + String.join(", ", article.getHashtags()));
        categoryLabel.setText("Category: " + (article.getCategory() != null ? String.join(", ", article.getCategory()) : ""));
        contentTextArea.setText(article.getContent());
    }




}
