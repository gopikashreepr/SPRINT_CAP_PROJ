package payloads;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Builds every request body as a Map (RestAssured serializes Maps to JSON
 * automatically). Centralizing this means NO step definition ever
 * string-concatenates raw JSON, and every value passed in comes from
 * TestContext or config -- never a literal typed twice.
 */
public class Payloads {

    public static Map<String, Object> loginOrCreateUser(String username, String password) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("userName", username);
        body.put("password", password);
        return body;
    }

    public static Map<String, Object> addBooks(String userId, List<String> isbns) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("userId", userId);

        List<Map<String, String>> collection = new ArrayList<>();
        for (String isbn : isbns) {
            Map<String, String> item = new LinkedHashMap<>();
            item.put("isbn", isbn);
            collection.add(item);
        }
        body.put("collectionOfIsbns", collection);
        return body;
    }

    public static Map<String, Object> deleteBook(String isbn, String userId) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("isbn", isbn);
        body.put("userId", userId);
        return body;
    }

    public static Map<String, Object> replaceBook(String userId, String newIsbn) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("userId", userId);
        body.put("isbn", newIsbn);
        return body;
    }
}
