/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietlong.app.article;

/**
 *
 * @author SingPC
 */

import java.util.Comparator;

public class ArticleCompareById implements Comparator<Article>{
    
    public int compare(Article article1, Article article2){
        return Integer.compare(article1.getArticleId(), article2.getArticleId());
    }
}
