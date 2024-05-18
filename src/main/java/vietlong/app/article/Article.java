/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietlong.app.article;

/**
 *
 * @author SingPC
 */
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import static vietlong.app.search_engine.Preprocessing.tokenize;


public class Article {
    public static final Comparator<Article> COMPARE_BY_ID = new 
        ArticleCompareById();
    public static final Comparator<Article> COMPARE_BY_CREATION_DATE = new 
        ArticleCompareByCreationDate();
    
    private static int articleCount = 0; 
    private final int articleId; 
    private String url;
    private ArticleType articleType;
    private String articleSummary; 
    private String title; 
    private String content; 
    private Date creationDate;
    private List<String> hashtags;
    private List<String> author; 
    private List<String> category; 

    public Article(){
        this.articleId = articleCount++;
    }

    public Article(
            String url, 
            String articleType, 
            String title, 
            String content, 
            String creationDate, 
            List<String> hashtags, 
            List<String> author, 
            List<String> category) throws ParseException {
        
        this.articleId = articleCount++;
        this.url = url;
        
        this.articleType = convertToArticleType(articleType);
        this.title = title;
        this.content = content;
        this.creationDate = convertCreationDateTime(creationDate) ;
        this.hashtags = hashtags;
        this.author = author;
        this.category = category;

        
    }

    public int getArticleId() {
        return articleId;
    }

    public String getUrl() {
        return url;
    }

    

    public ArticleType getArticleType() {
        return articleType;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public List<String> getAuthor() {
        return author;
    }

    public List<String> getCategory() {
        return category;
    }

    public static void setArticleCount(int articleCount) {
        Article.articleCount = articleCount;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public void setArticleSummary(String articleSummary) {
        this.articleSummary = articleSummary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreationDate(String creationDate) throws ParseException {
        this.creationDate = convertCreationDateTime(creationDate);
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
    
    private ArticleType convertToArticleType(String type){
        switch (type.toUpperCase().trim()) {
            case "NEWS" -> {
                return ArticleType.NEWS;
            }
            case "TWEET" -> {
                return ArticleType.TWEET;
            }
            case "BLOG_POST" -> {
                return ArticleType.BLOG_POST;
            }
            case "FACEBOOK_POST" -> {
                return ArticleType.FACEBOOK_POST;
            }
            default -> throw new IllegalArgumentException("Invalid article type: " + type);
        }
    }
    
    public Date convertCreationDateTime(String creationDateTime) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(creationDateTime);
    }
    
    public String getFormattedCreationDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.creationDate);
    }
    
    public enum ArticleType{
        TWEET,
        NEWS,
        BLOG_POST,
        FACEBOOK_POST,
    }
    
    @Override
    public String toString() {
        return "Article{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationDateTime=" + getFormattedCreationDate() +
                ", hashtag=" + hashtags +
                ", author=" + author +
                ", category=" + category +
                ", type=" + articleType +
                '}';
    }

    public List<String> tokenizeTitle() {
        return tokenize(this.title);
    }

    public List<String> tokenizeContent() {
        return tokenize(this.content);
    }
    
    
}
