package vietlong.app.test;
import vietlong.app.article.Article;
import vietlong.app.search_engine.SearchTool;
import vietlong.app.search_engine.Preprocessing;
import vietlong.app.search_engine.SortArticleByDate;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


import static vietlong.app.article.JsonArticleReader.readFromDirectory;

public class SearchToolTest {
    public static void main(String[] args) throws ParseException, IOException {
        // Tạo danh sách các bài viết mẫu
        List<Article> articles = readFromDirectory("Data", "data_full.json");
        // Tạo đối tượng SearchTool
        SearchTool searchTool = new SearchTool();

        // Tạo Scanner để đọc dữ liệu đầu vào từ người dùng
        Scanner scanner = new Scanner(System.in);

        // Nhập từ khóa tìm kiếm
        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine();

        // Áp dụng quá trình tokenize cho từ khóa
        List<String> keywordTokens = Preprocessing.tokenize(keyword);

        // Chuyển đổi danh sách các từ khóa đã tokenize thành một chuỗi đơn lẻ, các từ được phân tách bởi dấu cách
        String keywordTokenString = String.join(" ", keywordTokens);

        // Nhập tiêu chí tìm kiếm, tách các tiêu chí bằng dấu phẩy
        System.out.print("Enter search criteria (title, author, hashtag, content) separated by commas: ");
        String criteriaInput = scanner.nextLine();
        List<String> criteriaList = Arrays.stream(criteriaInput.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        // Thực hiện tìm kiếm
        List<Article> searchResult = searchTool.search(articles, keywordTokenString, criteriaList);
        if (searchResult.isEmpty()) {
            System.out.println("No articles found for the given keyword and criteria.");
        } else {
            System.out.println("Search results:"+searchResult.size());
            searchResult.forEach(System.out::println);

            // Yêu cầu người dùng nhập true hoặc false để chọn cách sắp xếp kết quả
            System.out.print("Sort results by time (true for newest/false for oldest): ");
            boolean sortByTime = scanner.nextBoolean();

            // Sắp xếp kết quả tìm kiếm theo thời gian
            SortArticleByDate.sortByTime(searchResult, sortByTime);
            System.out.println("Sorted search results:");
            searchResult.forEach(System.out::println);
        }
    }
}