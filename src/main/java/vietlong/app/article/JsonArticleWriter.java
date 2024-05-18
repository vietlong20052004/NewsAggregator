package vietlong.app.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class JsonArticleWriter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeToFile(List<Article> articles, String directory, String fileName) throws IOException {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, articles);
    }

    public static List<Article> readFromFile(String directory, String fileName) throws IOException {
        File file = new File(directory, fileName);
        if (file.exists()) {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Article.class);
            return objectMapper.readValue(file, listType);
        } else {
            return new ArrayList<>();
        }
    }
}
