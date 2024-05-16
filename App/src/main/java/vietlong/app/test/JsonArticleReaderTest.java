package vietlong.app.test;
import vietlong.app.article.Article;
import vietlong.app.article.JsonArticleReader;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static vietlong.app.article.JsonArticleReader.readFromDirectory;

public class JsonArticleReaderTest {
    public static void main(String[] args) throws ParseException, IOException {
        List<Article> articles = readFromDirectory("Data", "data_full.json");
        Article firstArticle = articles.get(0);
        System.out.println(firstArticle);
    }
}
