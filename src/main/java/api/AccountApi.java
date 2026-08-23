package api;

import endpoints.Routes;
import io.restassured.response.Response;
import payloads.Payloads;

import static io.restassured.RestAssured.given;

/**
 * One method per Account endpoint. Step definitions call these instead of
 * writing RestAssured calls directly -- keeps step defs thin/readable and
 * makes every call reusable across as many feature files/scenarios as needed.
 */
public class AccountApi extends BaseApi {

    public Response createUser(String username, String password) {
        return given()
                .spec(baseSpec())
                .body(Payloads.loginOrCreateUser(username, password))
                .when()
                .post(Routes.CREATE_USER);
    }

    public Response generateToken(String username, String password) {
        return given()
                .spec(baseSpec())
                .body(Payloads.loginOrCreateUser(username, password))
                .when()
                .post(Routes.GENERATE_TOKEN);
    }

    public Response isAuthorized(String username, String password) {
        return given()
                .spec(baseSpec())
                .body(Payloads.loginOrCreateUser(username, password))
                .when()
                .post(Routes.AUTHORIZED);
    }

    public Response getUser(String userId, String token) {
        return given()
                .spec(authSpec(token))
                .when()
                .get(Routes.getUserById(userId));
    }

    /** Overload for the negative case where no token should be sent. */
    public Response getUserWithoutAuth(String userId) {
        return given()
                .spec(baseSpec())
                .when()
                .get(Routes.getUserById(userId));
    }

    public Response deleteUser(String userId, String token) {
        return given()
                .spec(authSpec(token))
                .when()
                .delete(Routes.deleteUserById(userId));
    }

    public Response deleteUserWithoutAuth(String userId) {
        return given()
                .spec(baseSpec())
                .when()
                .delete(Routes.deleteUserById(userId));
    }
}
