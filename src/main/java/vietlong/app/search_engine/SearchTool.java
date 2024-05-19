package vietlong.app.search_engine;


import vietlong.app.article.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;


public class SearchTool {

    public List<Article> search(List<Article> articles, String keyword, List<String> criteriaList) {
        List<String> keywords = Arrays.asList(keyword.toLowerCase().split(" "));
        List<Article> copyArticles = new ArrayList<>(articles);
        return copyArticles.parallelStream()
                .filter(article -> {
                    boolean matches = criteriaList.parallelStream().anyMatch(criteria ->
                            switch (criteria.toLowerCase()) {
                                case "title" -> keywords.parallelStream().anyMatch(kw -> article.tokenizeTitle().contains(kw));
                                case "author" -> article.getAuthor().parallelStream().anyMatch(a -> keywords.stream().anyMatch(kw -> a.toLowerCase().contains(kw)));
                                case "hashtag" -> article.getHashtags().parallelStream().anyMatch(h -> keywords.stream().anyMatch(kw -> h.toLowerCase().contains(kw)));
                                case "content" -> keywords.parallelStream().anyMatch(kw -> article.tokenizeContent().contains(kw));
                                default -> false;
                            }
                    );
                    return matches;
                })
                .collect(Collectors.toList());
    }



}