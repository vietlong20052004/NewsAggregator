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

public class JsonArticleReader {
    
    public static List<Article> readFromFile(String directoryPath, String fileName) throws ParseException, IOException {
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
        return List.of(mapper.readValue(jsonString, Article[].class));
    }
    }


