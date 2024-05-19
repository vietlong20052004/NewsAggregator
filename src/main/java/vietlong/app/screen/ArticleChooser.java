package vietlong.app.screen;



import vietlong.app.article.Article;
import vietlong.app.person.User;
import vietlong.app.search_engine.SearchTool;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static vietlong.app.article.JsonArticleReader.readFromFile;

public class ArticleChooser extends JPanel {
    private List<Article> articles;
    private List<Article> searchResults;
    private int currentPage = 0;
    private final int articlesPerPage =5;
    private final MainApplication mainApp;
    private final User user;

    private JPanel articlePanel;
    private JButton nextButton;
    private JButton prevButton;
    private JTextField currentPageField;
    private JLabel totalPagesLabel;
    private boolean editMode = false;


    public ArticleChooser(MainApplication mainApp, User user) {
        this.mainApp = mainApp;
        this.user = user;
        setLayout(new BorderLayout());

        // Load articles from JSON file
        articles = loadArticles();

        // Initialize components
        initializeComponents();

        updateArticles();
    }

    private void initializeComponents(){
        // Add background
        BackgroundPanel backgroundPanel = new BackgroundPanel("ImageIcon/background.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel, BorderLayout.CENTER);

        // Add search panel
        SearchBar searchPanel = new SearchBar(this, articles);
        searchPanel.setOpaque(false);
        backgroundPanel.add(searchPanel, BorderLayout.NORTH);




        articlePanel = new JPanel();
        articlePanel.setLayout(new BoxLayout(articlePanel, BoxLayout.Y_AXIS));
        articlePanel.setBorder(new EmptyBorder(10, 30, 10, 30));
        articlePanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(articlePanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);


        JPanel controlPanel = createControlPanel();
        backgroundPanel.add(controlPanel, BorderLayout.SOUTH);

    }


    private JPanel createControlPanel(){
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);
        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");
        controlPanel.add(prevButton);
        controlPanel.add(nextButton);

        currentPageField = new JTextField(4);
        currentPageField.setHorizontalAlignment(JTextField.CENTER);
        totalPagesLabel = new JLabel();

        controlPanel.add(prevButton);
        controlPanel.add(new JLabel("Page "));
        controlPanel.add(currentPageField);
        controlPanel.add(new JLabel(" of "));
        controlPanel.add(totalPagesLabel);
        controlPanel.add(nextButton);

        addControlPanelListeners();

        return controlPanel;
    }

    private void addControlPanelListeners(){
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 0) {
                    currentPage--;
                    updateArticles();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((currentPage + 1) * articlesPerPage < articles.size()) {
                    currentPage++;
                    updateArticles();
                }
            }
        });

        currentPageField.addActionListener(e->{
            try{
                int page = Integer.parseInt(currentPageField.getText())-1;
                if (page>=0 && page <(articles.size() +articlesPerPage-1) / articlesPerPage){
                    currentPage = page;
                    updateArticles();
                } else {
                    JOptionPane.showMessageDialog(this,"Invalid page number");
                }
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Invalid page number");

            }
        });
    }

    public static List<Article> loadArticles() {
        try {
            return readFromFile("Data", "data_full.json");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load articles: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void updateArticles() {
        articlePanel.removeAll();
        int start = currentPage * articlesPerPage;
        int end = Math.min(start + articlesPerPage, articles.size());

        Box articleContainer = Box.createVerticalBox();
        for (int i = start; i < end; i++) {
            Article article = articles.get(i);
            JPanel articleItem = createArticleItem(article);

            articleItem.setAlignmentX(Component.CENTER_ALIGNMENT);

            articleContainer.add(articleItem);
            articleContainer.add(Box.createVerticalStrut(10));
        }
        articleContainer.add(Box.createVerticalGlue());
        articlePanel.add(articleContainer);

        articlePanel.revalidate();
        articlePanel.repaint();

        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled((currentPage + 1) * articlesPerPage < articles.size());

        // Update page info
        currentPageField.setText(String.valueOf(currentPage + 1));
        totalPagesLabel.setText(String.valueOf((articles.size() + articlesPerPage - 1) / articlesPerPage));
    }

    private JPanel createArticleItem(Article article){
        JPanel articleItem = new JPanel();

        articleItem.setLayout(new BoxLayout(articleItem, BoxLayout.Y_AXIS));
        articleItem.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 4,true),
                new EmptyBorder(10, 10, 10, 10)));
        articleItem.setPreferredSize(new Dimension(800, 100));
        articleItem.setBackground(Color.cyan);

        String titleText = article.getTitle();
        JLabel titleLabel = new JLabel("Title: " + titleText );
        titleLabel.setFont(new Font("Serif", Font.BOLD, 16));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel authorLabel = new JLabel("Author: " + String.join(", ", article.getAuthor()));
        authorLabel.setFont(new Font("SansSerif", Font.ITALIC,14));
        authorLabel.setForeground(Color.DARK_GRAY);
        authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dateLabel = new JLabel("Date: " + article.convertToFormattedDate());
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dateLabel.setForeground(Color.DARK_GRAY);
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String hashtagsText = String.join(", #", article.getHashtags());
        if (hashtagsText.length() > 120) {
            hashtagsText = hashtagsText.substring(0, 120) + "...";
        }
        JLabel hashtagsLabel = new JLabel("<html><div style='width: 750px;'>Hashtags: #" + hashtagsText + "</div></html>");
        hashtagsLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        hashtagsLabel.setForeground(Color.DARK_GRAY);
        hashtagsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        hashtagsLabel.setMaximumSize(new Dimension(700,25));

        articleItem.add(titleLabel);
        articleItem.add(authorLabel);
        articleItem.add(dateLabel);
        articleItem.add(hashtagsLabel);

        if (editMode){
            articleItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(() -> new ArticleEditor(mainApp, user, articles, article).setVisible(true));
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    articleItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    articleItem.setBackground(Color.PINK);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    articleItem.setCursor(Cursor.getDefaultCursor());
                    articleItem.setBackground(Color.CYAN);
                }
            });
        } else {
            articleItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(() -> new ArticleViewer(article).setVisible(true));
                }
                @Override
                public void mouseEntered(MouseEvent e){
                    articleItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    articleItem.setBackground(new Color(230,250,250));
                }
                @Override
                public void mouseExited(MouseEvent e){
                    articleItem.setCursor(Cursor.getDefaultCursor());
                    articleItem.setBackground(Color.CYAN);
                }


            });
        }


        return articleItem;
    }

    public void toggleEditMode(){
        editMode = !editMode;
        updateArticles();
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void displaySearchResults(List<Article> searchResults) {
        this.articles = new ArrayList<>(searchResults);
        currentPage = 0;
        updateArticles();
    }
}
