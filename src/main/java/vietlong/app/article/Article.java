/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietlong.app.article;

/**
 *
 * @author SingPC
 */
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import static vietlong.app.search_engine.Preprocessing.tokenize;


public class Article {

    private String url;
    private ArticleType articleType;
    private String title; 
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishedDate;
    private List<String> hashtags;
    private List<String> author; 
    private List<String> category; 

    public Article(){

    }

    public Article(
            @JsonProperty("url") String url,
            @JsonProperty("type") String articleType,
            @JsonProperty("title") String title,
            @JsonProperty("content") String content,
            @JsonProperty("publishedDate") String publishedDate,
            @JsonProperty("hashtags") List<String> hashtags,
            @JsonProperty("author") List<String> author,
            @JsonProperty("category") List<String> category) throws ParseException {

        this.url = url;
        
        this.articleType = ArticleType.fromString(articleType);
        this.title = title;
        this.content = content;
        this.publishedDate = Article.convertPublishedDateTime(publishedDate) ;
        this.hashtags = hashtags;
        this.author = author;
        this.category = category;

        
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

    public Date getPublishedDate() {
        return publishedDate;
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


    public void setUrl(String url) {
        this.url = url;
    }


    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishedDate(String publishedDate) throws ParseException {
        this.publishedDate = Article.convertPublishedDateTime(publishedDate);
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
    

    
    public static Date convertPublishedDateTime(String publishedDateTime) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(publishedDateTime.trim());
    }

    
    public enum ArticleType{
        TWEET,
        NEWS,
        BLOG_POST,
        FACEBOOK_POST;

        @JsonCreator
        public static ArticleType fromString(String key) {
            return key == null ? null : ArticleType.valueOf(key.toUpperCase());
        }

        @JsonValue
        public String toValue() {
            return this.name().toLowerCase();
        }
    }
    
    @Override
    public String toString() {
        return "Article{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creationDateTime=" + publishedDate +
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
