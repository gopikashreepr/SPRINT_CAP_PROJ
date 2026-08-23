package api;

import config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Every API helper class extends this to get a pre-configured RequestSpecification:
 * baseUrl (from ConfigReader, so it automatically switches between Live/Mock based
 * on -Denv), Content-Type, and timeouts. No endpoint helper class builds its own
 * base request from scratch, keeping the framework DRY and consistent.
 */
public abstract class BaseApi {

    protected RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getInstance().getBaseUrl())
                .setContentType(ContentType.JSON)
                .build();
    }

    /** Same as baseSpec() but with a Bearer token attached, for authenticated calls. */
    protected RequestSpecification authSpec(String token) {
        return baseSpec().header("Authorization", "Bearer " + token);
    }
}
