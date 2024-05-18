package vietlong.app.search_engine;

import vietlong.app.article.Article;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;

public class SearchTool {
    public List<Article> search(List<Article> articles, String keyword, List<String> criteriaList) {
        return articles.stream()
                .sorted(Comparator.comparingInt(article -> {
                    Map<String, Integer> keywordCounts = new HashMap<>();
                    Arrays.stream(keyword.split(" "))
                            .forEach(kw -> {
                                int count = (int) article.tokenizeTitle().stream().filter(token -> token.equals(kw)).count() +
                                        (int) article.tokenizeContent().stream().filter(token -> token.equals(kw)).count();
                                keywordCounts.put(kw, count);
                            });
                    return -keywordCounts.values().stream().mapToInt(Integer::intValue).sum();
                }))
                .filter(article -> {
                    boolean containsAnyKeyword = Arrays.stream(keyword.split(" "))
                            .anyMatch(kw -> article.tokenizeTitle().contains(kw) || article.tokenizeContent().contains(kw));
                    return criteriaList.stream().anyMatch(criteria ->
                            switch (criteria.toLowerCase()) {
                                case "title" -> article.tokenizeTitle().contains(keyword);
                                case "author" -> article.getAuthor().stream().anyMatch(a -> a.toLowerCase().contains(keyword.toLowerCase()));
                                case "hashtag" -> article.getHashtags().stream().anyMatch(h -> h.toLowerCase().contains(keyword.toLowerCase()));
                                case "content" -> containsAnyKeyword;
                                default -> false;
                            }
                    );
                })
                .collect(Collectors.toList());
    }

    private boolean tokenizeAndSearch(List<String> tokens, String keyword) {
        return tokens.contains(keyword.toLowerCase());
    }
}