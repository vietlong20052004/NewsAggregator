/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietlong.app.article;

import java.util.Comparator;

/**
 *
 * @author SingPC
 */
public class ArticleCompareByCreationDate implements Comparator<Article> {
    
    public int compare(Article article1, Article article2) {
        // Compare the creation dates of the articles
        return article1.getCreationDate().compareTo(article2.getCreationDate());
    }
}
