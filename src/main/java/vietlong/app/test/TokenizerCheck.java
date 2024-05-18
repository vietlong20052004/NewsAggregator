package vietlong.app.test;

import vietlong.app.article.Article;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static vietlong.app.article.JsonArticleReader.readFromFile;

public class TokenizerCheck {
    public static void main(String[] args) throws ParseException, IOException {
        List<Article> articles = readFromFile("Data", "data_full.json");
        Article firstArticle = articles.getFirst();
        System.out.println(firstArticle);

        // Tokenize the content
        List<String> contentTokens =firstArticle.tokenizeTitle();

        // Print the tokenized content
        System.out.println("Tokenized Title:");
        contentTokens.forEach(System.out::println);
    }
}
