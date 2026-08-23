package utilities;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

/**
 * Thin, reusable wrapper around REST Assured's json-schema-validator module.
 * Schemas live in src/test/resources/testdata/schemas/*.json (not hardcoded
 * inline) and are referenced by filename only, keeping step definitions clean.
 *
 * Usage from a step definition:
 *   JsonSchemaValidatorUtil.validate(context.getLastResponse(), "user-schema.json");
 */
public class JsonSchemaValidatorUtil {

    private static final String SCHEMA_BASE_PATH = "testdata/schemas/";

    public static void validate(Response response, String schemaFileName) {
        response.then().assertThat().body(
                JsonSchemaValidator.matchesJsonSchemaInClasspath(SCHEMA_BASE_PATH + schemaFileName)
        );
    }
}
