/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietlong.app.article;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import vietlong.app.utils.FileExistChecker;
/**
 *
 * @author SingPC
 */
public class ParsedArticle {
    @JsonProperty("url")
    private String url;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("publishedDate")
    private String publishDate;

    @JsonProperty("hashtags")
    private List<String> hashtags;

    @JsonProperty("author")
    private List<String> author;

    @JsonProperty("category")
    private List<String> category;

    @JsonProperty("type")
    private String type;

    
    
    
    public static void main(String[] args) throws IOException {
        String directoryPath = "src/main/java/Data";
        String fileName = "data_full.json";

        File file = new File(directoryPath, fileName);

        if (FileExistChecker.checkFileExist(directoryPath, fileName)) {
            System.out.println("File exists in the directory.");
        } else {
            System.out.println("File does not exist in the directory.");
        }
        byte[] jsonData = Files.readAllBytes(file.toPath());
        String jsonString = new String(jsonData, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper
                   .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES)
                   .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                   .enable(JsonParser.Feature.ALLOW_COMMENTS);
        ParsedArticle[] newsArticles = objectMapper.readValue(jsonString, ParsedArticle[].class);
        for (ParsedArticle article : newsArticles) {
            System.out.println(article.getTitle());
        }
    }
    
    public String getTitle() {
        return title;
    }
    

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return content;
    }

    public String getPublishDate() {
        return publishDate;
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

    public String getType() {
        return type;
    }
    
}
