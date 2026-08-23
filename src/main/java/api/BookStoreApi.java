package api;

import endpoints.Routes;
import io.restassured.response.Response;
import payloads.Payloads;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * One method per BookStore endpoint, mirroring AccountApi's pattern.
 */
public class BookStoreApi extends BaseApi {

    public Response getAllBooks() {
        return given()
                .spec(baseSpec())
                .when()
                .get(Routes.BOOKS);
    }

    public Response addBooks(String userId, List<String> isbns, String token) {
        return given()
                .spec(authSpec(token))
                .body(Payloads.addBooks(userId, isbns))
                .when()
                .post(Routes.BOOKS);
    }

    public Response deleteAllBooks(String userId, String token) {
        return given()
                .spec(authSpec(token))
                .queryParam("UserId", userId)
                .when()
                .delete(Routes.BOOKS);
    }

    public Response deleteAllBooksWithoutAuth(String userId) {
        return given()
                .spec(baseSpec())
                .queryParam("UserId", userId)
                .when()
                .delete(Routes.BOOKS);
    }

    public Response getBookByIsbn(String isbn) {
        return given()
                .spec(baseSpec())
                .queryParam("ISBN", isbn)
                .when()
                .get(Routes.BOOK);
    }

    public Response deleteBook(String isbn, String userId, String token) {
        return given()
                .spec(authSpec(token))
                .body(Payloads.deleteBook(isbn, userId))
                .when()
                .delete(Routes.BOOK);
    }

    public Response replaceBook(String isbnToReplace, String userId, String newIsbn, String token) {
        return given()
                .spec(authSpec(token))
                .body(Payloads.replaceBook(userId, newIsbn))
                .when()
                .put(Routes.replaceBookByIsbn(isbnToReplace));
    }

    public Response replaceBookWithoutAuth(String isbnToReplace, String userId, String newIsbn) {
        return given()
                .spec(baseSpec())
                .body(Payloads.replaceBook(userId, newIsbn))
                .when()
                .put(Routes.replaceBookByIsbn(isbnToReplace));
    }
}
