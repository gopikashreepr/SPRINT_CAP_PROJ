package stepdefinitions;

import api.BookStoreApi;
import config.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import utilities.TestContext;

import java.util.Collections;
import java.util.List;

/**
 * Step definitions for all BookStore.*.feature files (GetBooks, AddBooks,
 * GetBookByIsbn, ReplaceBook, DeleteBook, DeleteAllBooks).
 *
 * Shared assertion steps ("the response status code should be {int}" and
 * "the response should match the {string} schema") live in
 * AccountStepDefinitions to avoid duplicate step definitions -- Cucumber
 * step matching is global across all step definition classes.
 *
 * ISBN placeholders like "isbn1" / "isbn2" / "invalidIsbn" in feature files
 * are resolved here against config.properties, never hardcoded in the
 * feature files or here.
 */
public class BookStoreStepDefinitions {

    private final TestContext context;
    private final BookStoreApi bookStoreApi = new BookStoreApi();
    private final ConfigReader config = ConfigReader.getInstance();

    public BookStoreStepDefinitions(TestContext context) {
        this.context = context;
    }

    /** Resolves the symbolic ISBN key used in feature files to its configured value. */
    private String resolveIsbn(String key) {
        switch (key) {
            case "isbn1":
                return config.getIsbn1();
            case "isbn2":
                return config.getIsbn2();
            case "invalidIsbn":
                return config.getInvalidIsbn();
            default:
                // Allows a scenario to pass a literal ISBN directly if ever needed
                return key;
        }
    }

    // ---------------- Background: seed a book into the collection ----------------

    @Given("ISBN {string} has been added to the user's collection")
    public void isbnHasBeenAddedToCollection(String isbnKey) {
        String isbn = resolveIsbn(isbnKey);
        Response response = bookStoreApi.addBooks(context.getUserId(), Collections.singletonList(isbn), context.getToken());
        context.setLastResponse(response);
        Assert.assertEquals(response.getStatusCode(), 201, "Precondition failed: could not seed ISBN " + isbn);
        context.set("lastAddedIsbn", isbn);
    }

    // ---------------- Get All Books ----------------

    @When("I request all books from the catalog")
    public void iRequestAllBooksFromCatalog() {
        Response response = bookStoreApi.getAllBooks();
        context.setLastResponse(response);
    }

    // ---------------- Add Books ----------------

    @When("I add ISBN {string} to the user's collection using the saved token")
    public void iAddIsbnToCollection(String isbnKey) {
        String isbn = resolveIsbn(isbnKey);
        List<String> isbns = Collections.singletonList(isbn);
        Response response = bookStoreApi.addBooks(context.getUserId(), isbns, context.getToken());
        context.setLastResponse(response);
    }

    @When("I add an empty list of ISBNs to the user's collection using the saved token")
    public void iAddEmptyListOfIsbns() {
        Response response = bookStoreApi.addBooks(context.getUserId(), Collections.emptyList(), context.getToken());
        context.setLastResponse(response);
    }

    // ---------------- Get Book by ISBN ----------------

    @When("I request the book with ISBN {string}")
    public void iRequestBookWithIsbn(String isbnKey) {
        String isbn = resolveIsbn(isbnKey);
        Response response = bookStoreApi.getBookByIsbn(isbn);
        context.setLastResponse(response);
    }

    @When("I request the book with an empty ISBN")
    public void iRequestBookWithEmptyIsbn() {
        Response response = bookStoreApi.getBookByIsbn("");
        context.setLastResponse(response);
    }

    // ---------------- Replace Book ----------------

    @When("I replace ISBN {string} with ISBN {string} using the saved token")
    public void iReplaceIsbnWithIsbn(String oldKey, String newKey) {
        String oldIsbn = resolveIsbn(oldKey);
        String newIsbn = resolveIsbn(newKey);
        Response response = bookStoreApi.replaceBook(oldIsbn, context.getUserId(), newIsbn, context.getToken());
        context.setLastResponse(response);
    }

    @When("I replace ISBN {string} with ISBN {string} without a token")
    public void iReplaceIsbnWithIsbnWithoutToken(String oldKey, String newKey) {
        String oldIsbn = resolveIsbn(oldKey);
        String newIsbn = resolveIsbn(newKey);
        Response response = bookStoreApi.replaceBookWithoutAuth(oldIsbn, context.getUserId(), newIsbn);
        context.setLastResponse(response);
    }

    // ---------------- Delete Book ----------------

    @When("I delete ISBN {string} from the user's collection using the saved token")
    public void iDeleteIsbnFromCollection(String isbnKey) {
        String isbn = resolveIsbn(isbnKey);
        Response response = bookStoreApi.deleteBook(isbn, context.getUserId(), context.getToken());
        context.setLastResponse(response);
    }

    // ---------------- Delete All Books ----------------

    @When("I delete all books from the user's collection using the saved token")
    public void iDeleteAllBooksWithToken() {
        Response response = bookStoreApi.deleteAllBooks(context.getUserId(), context.getToken());
        context.setLastResponse(response);
    }

    @When("I delete all books from the user's collection without a token")
    public void iDeleteAllBooksWithoutToken() {
        Response response = bookStoreApi.deleteAllBooksWithoutAuth(context.getUserId());
        context.setLastResponse(response);
    }
}
