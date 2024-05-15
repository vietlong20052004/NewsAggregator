package vietlong.app.screen;



import vietlong.app.article.Article;
import vietlong.app.person.User;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


import java.text.ParseException;
import java.util.List;

import static vietlong.app.article.JsonArticleReader.readFromDirectory;

public class ArticleChooser extends JPanel {
    private final List<Article> articles;
    private int currentPage = 0;
    private final int articlesPerPage = 5;
    private final User user;

    private JPanel articlePanel;
    private JButton nextButton;
    private JButton prevButton;
    private JTextField currentPageField;
    private JLabel totalPagesLabel;

    public ArticleChooser(User user) {
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
        backgroundPanel.setLayout(null);
        add(backgroundPanel, BorderLayout.CENTER);

        articlePanel = new JPanel();
        articlePanel.setOpaque(false);
        articlePanel.setLayout(new BoxLayout(articlePanel, BoxLayout.Y_AXIS));
        articlePanel.setBorder(new EmptyBorder(10, 30, 10, 30));

        JScrollPane scrollPane = new JScrollPane(articlePanel);
        scrollPane.setBounds(30, 20, 650, 130*articlesPerPage);
        scrollPane.setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = createControlPanel();
        controlPanel.setBounds(30, 130*articlesPerPage +20, 600, 40);
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

    private List<Article> loadArticles() {
        try {
            return readFromDirectory("Data", "data_full.json");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load articles: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void updateArticles() {
        articlePanel.removeAll();

        int start = currentPage * articlesPerPage;
        int end = Math.min(start + articlesPerPage, articles.size());


        for (int i = start; i < end; i++) {
            Article article = articles.get(i);
            JPanel articleItem = createArticleItem(article);

            articlePanel.add(articleItem);
            articlePanel.add(Box.createVerticalStrut(5)); // Add spacing between articles

        }

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
        articleItem.setMaximumSize(new Dimension(600, 100));
        articleItem.setBackground(Color.cyan);
        JLabel titleLabel = new JLabel("Title: " + article.getTitle());
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        titleLabel.setForeground(Color.DARK_GRAY);


        JLabel authorLabel = new JLabel("Author: " + String.join(", ", article.getAuthor()));
        authorLabel.setFont(new Font("SansSerif", Font.ITALIC,14));
        authorLabel.setForeground(Color.DARK_GRAY);
        JLabel dateLabel = new JLabel("Date: " + article.getFormattedCreationDate());
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dateLabel.setForeground(Color.DARK_GRAY);

        articleItem.add(titleLabel);
        articleItem.add(authorLabel);
        articleItem.add(dateLabel);

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

        return articleItem;
    }



}
