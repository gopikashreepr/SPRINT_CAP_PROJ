package utilities;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds every value that gets CHAINED between steps/requests within a single
 * scenario: token, userId, username, password, last response, generated ISBNs, etc.
 *
 * This replaces "hardcoded" values inside feature files/step defs. A step that
 * generates a token stores it here; every later step reads it from here instead
 * of a literal string. Cucumber gives each scenario a fresh instance when this
 * class is registered as a PicoContainer/dependency-injected object (see Hooks
 * + step definition constructors), so state never leaks between scenarios.
 */
public class TestContext {

    private final Map<String, Object> store = new HashMap<>();
    private Response lastResponse;

    public void set(String key, Object value) {
        store.put(key, value);
    }

    public Object get(String key) {
        return store.get(key);
    }

    public String getString(String key) {
        Object value = store.get(key);
        return value == null ? null : value.toString();
    }

    public boolean has(String key) {
        return store.containsKey(key) && store.get(key) != null;
    }

    public void setLastResponse(Response response) {
        this.lastResponse = response;
    }

    public Response getLastResponse() {
        return lastResponse;
    }

    // Convenience named accessors for the values used most often across steps
    public void setToken(String token) { set("token", token); }
    public String getToken() { return getString("token"); }

    public void setUserId(String userId) { set("userId", userId); }
    public String getUserId() { return getString("userId"); }

    public void setUsername(String username) { set("username", username); }
    public String getUsername() { return getString("username"); }

    public void setPassword(String password) { set("password", password); }
    public String getPassword() { return getString("password"); }
}
