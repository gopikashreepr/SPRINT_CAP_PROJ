package stepdefinitions;

import api.AccountApi;
import config.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;
import utilities.JsonSchemaValidatorUtil;
import utilities.RandomDataGenerator;
import utilities.TestContext;

/**
 * Step definitions for all Account.*.feature files (CreateUser, GenerateToken,
 * GetUser, DeleteUser). TestContext is injected by Cucumber's PicoContainer,
 * so this class holds no mutable fields of its own beyond the shared context
 * and the reusable AccountApi helper.
 */
public class AccountStepDefinitions {

    private final TestContext context;
    private final AccountApi accountApi = new AccountApi();

    public AccountStepDefinitions(TestContext context) {
        this.context = context;
    }

    // ---------------- Shared / Background steps ----------------

    @Given("the BookStore API base URL is configured")
    public void theBaseUrlIsConfigured() {
        // No-op assertion step: proves ConfigReader resolves a baseUrl for the
        // active -Denv without throwing, and documents the precondition in the
        // report. Actual usage happens inside AccountApi/BookStoreApi via BaseApi.
        String baseUrl = ConfigReader.getInstance().getBaseUrl();
        Assert.assertNotNull(baseUrl, "baseUrl must be configured for the active environment");
    }

    @Given("I generate a unique username and a strong password")
    public void iGenerateUniqueUsernameAndPassword() {
        context.setUsername(RandomDataGenerator.uniqueUsername());
        context.setPassword(RandomDataGenerator.strongPassword());
    }

    @Given("I generate a unique username")
    public void iGenerateUniqueUsername() {
        context.setUsername(RandomDataGenerator.uniqueUsername());
    }

    @Given("I generate a strong password")
    public void iGenerateStrongPassword() {
        context.setPassword(RandomDataGenerator.strongPassword());
    }

    @Given("I set the username to {string}")
    public void iSetTheUsernameTo(String username) {
        context.setUsername(username);
    }

    @Given("I set the password to {string}")
    public void iSetThePasswordTo(String password) {
        context.setPassword(password);
    }

    @Given("a user already exists with a unique username and a strong password")
    public void aUserAlreadyExists() {
        context.setUsername(RandomDataGenerator.uniqueUsername());
        context.setPassword(RandomDataGenerator.strongPassword());
        Response response = accountApi.createUser(context.getUsername(), context.getPassword());
        context.setLastResponse(response);
        Assert.assertEquals(response.getStatusCode(), 201, "Precondition failed: could not create user");
        context.setUserId(response.jsonPath().getString("userID"));
    }

    @Given("I have generated a valid token for that user")
    public void iHaveGeneratedValidToken() {
        Response response = accountApi.generateToken(context.getUsername(), context.getPassword());
        context.setLastResponse(response);
        Assert.assertEquals(response.getStatusCode(), 200, "Precondition failed: could not generate token");
        context.setToken(response.jsonPath().getString("token"));
    }

    // ---------------- Create User ----------------

    @When("I send a request to create the user")
    public void iSendRequestToCreateUser() {
        Response response = accountApi.createUser(context.getUsername(), context.getPassword());
        context.setLastResponse(response);
    }

    @Then("the created userId should be saved for later steps")
    public void theCreatedUserIdShouldBeSaved() {
        String userId = context.getLastResponse().jsonPath().getString("userID");
        Assert.assertNotNull(userId, "Expected userID in response to chain into later steps");
        context.setUserId(userId);
    }

    // ---------------- Generate Token ----------------

    @When("I request a token for the existing user")
    public void iRequestTokenForExistingUser() {
        Response response = accountApi.generateToken(context.getUsername(), context.getPassword());
        context.setLastResponse(response);
    }

    @When("I request a token using an incorrect password")
    public void iRequestTokenWithIncorrectPassword() {
        Response response = accountApi.generateToken(context.getUsername(), "WrongPassword999!");
        context.setLastResponse(response);
    }

    @Then("the response status field should be {string}")
    public void theResponseStatusFieldShouldBe(String expectedStatus) {
        String actual = context.getLastResponse().jsonPath().getString("status");
        Assert.assertEquals(actual, expectedStatus);
    }

    @Then("the token should be saved for later steps")
    public void theTokenShouldBeSaved() {
        String token = context.getLastResponse().jsonPath().getString("token");
        Assert.assertNotNull(token, "Expected a token in the response to chain into later steps");
        context.setToken(token);
    }

    // ---------------- Get User ----------------

    @When("I request the user details using the saved userId and token")
    public void iRequestUserDetailsWithToken() {
        Response response = accountApi.getUser(context.getUserId(), context.getToken());
        context.setLastResponse(response);
    }

    @When("I request the user details using the saved userId but no token")
    public void iRequestUserDetailsWithoutToken() {
        Response response = accountApi.getUserWithoutAuth(context.getUserId());
        context.setLastResponse(response);
    }

    // ---------------- Delete User ----------------

    @When("I send a request to delete the saved user using the saved token")
    public void iDeleteSavedUserWithToken() {
        Response response = accountApi.deleteUser(context.getUserId(), context.getToken());
        context.setLastResponse(response);
    }

    @When("I send a request to delete the saved user without a token")
    public void iDeleteSavedUserWithoutToken() {
        Response response = accountApi.deleteUserWithoutAuth(context.getUserId());
        context.setLastResponse(response);
    }

    // ---------------- Shared assertions (used by every Account feature) ----------------

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedCode) {
        Assert.assertEquals(context.getLastResponse().getStatusCode(), expectedCode);
    }

    @And("the response should match the {string} schema")
    public void theResponseShouldMatchSchema(String schemaFileName) {
        JsonSchemaValidatorUtil.validate(context.getLastResponse(), schemaFileName);
    }
}
