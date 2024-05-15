/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vietlong.app.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import vietlong.app.utils.FileExistChecker;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SingPC
 */
public class JsonArticleReader {
    
    public static List<Article> readFromDirectory(String directoryPath, String fileName) throws ParseException, IOException {
        ObjectMapper mapper = new ObjectMapper();


        File file = new File(directoryPath, fileName);

        if (FileExistChecker.checkFileExist(directoryPath, fileName)) {
            System.out.println("File exists in the directory.");
        } else {
            System.out.println("File does not exist in the directory.");
        }
        byte[] jsonData = Files.readAllBytes(file.toPath());
        String jsonString = new String(jsonData, StandardCharsets.UTF_8);

            // Read JSON file and convert to list of articles
        ParsedArticle[] articles = mapper.readValue(jsonString, ParsedArticle[].class);
        List<Article> listArticle = new ArrayList<>();
            // Print the articles
        for (ParsedArticle article : articles) {
            Article convertedArticle = new Article(
                       article.getUrl(),
                       article.getType(),
                       article.getTitle(),
                       article.getContent(),
                       article.getPublishDate(),
                       article.getHashtags(),
                       article.getAuthor(),
                       article.getCategory()
                );
            listArticle.add(convertedArticle);
            }


        return listArticle;
    }
    }


