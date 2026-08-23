package endpoints;

/**
 * Every endpoint PATH used by the framework lives here as a constant.
 * Nobody outside this class should type a raw "/Account/..." or
 * "/BookStore/..." string. baseUrl itself comes from ConfigReader,
 * so a full URI is always: ConfigReader.getInstance().getBaseUrl() + Routes.X
 *
 * Path-parameterized routes are exposed as small helper methods so callers
 * never string-concatenate a UUID or ISBN by hand.
 */
public class Routes {

    // ---------------- Account module ----------------
    public static final String AUTHORIZED = "/Account/v1/Authorized";
    public static final String GENERATE_TOKEN = "/Account/v1/GenerateToken";
    public static final String CREATE_USER = "/Account/v1/User";

    public static String getUserById(String uuid) {
        return "/Account/v1/User/" + uuid;
    }

    public static String deleteUserById(String uuid) {
        return "/Account/v1/User/" + uuid;
    }

    // ---------------- BookStore module ----------------
    public static final String BOOKS = "/BookStore/v1/Books";
    public static final String BOOK = "/BookStore/v1/Book";

    public static String replaceBookByIsbn(String isbn) {
        return "/BookStore/v1/Books/" + isbn;
    }
}
