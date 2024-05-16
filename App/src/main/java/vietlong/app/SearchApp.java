package vietlong.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your search query: ");
        String query = scanner.nextLine();

        // Send search query to the Python backend
        String articles = sendSearchQuery(query);

        // Print the received articles
        System.out.println("Received Articles:");
        System.out.println(articles);

        scanner.close();
    }

    private static String sendSearchQuery(String query) {
        try {
            // Set up the connection
            URL url = new URL("http://localhost:5000/search");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send the request body (query)
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = query.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Close the connection
            conn.disconnect();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
