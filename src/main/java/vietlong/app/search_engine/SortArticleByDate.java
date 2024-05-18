package vietlong.app.search_engine;

import vietlong.app.article.Article;

import java.util.Comparator;
import java.util.List;

public class SortArticleByDate {
    public static void sortByTime(List<Article> searchResult, boolean sortByNewest) {
        if (sortByNewest) {
            searchResult.sort(Comparator.comparing(Article::getPublishedDate).reversed());
        } else {
            searchResult.sort(Comparator.comparing(Article::getPublishedDate));
        }
    }
}