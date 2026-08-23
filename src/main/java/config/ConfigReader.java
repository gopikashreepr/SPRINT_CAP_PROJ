package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads src/main/resources/config.properties exactly once (singleton) and
 * exposes typed getters. This is the ONLY class that should ever touch
 * config.properties directly -- everything else (Routes, ApiHelpers,
 * StepDefinitions) asks ConfigReader for values, so there is a single
 * source of truth and zero hardcoded values scattered across the framework.
 *
 * Active environment is selected via -Denv=live or -Denv=mock
 * e.g. mvn test -Denv=mock
 * Falls back to "default.env" in config.properties if -Denv is not passed.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();
    private static ConfigReader instance;

    private ConfigReader() {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in src/main/resources");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    private String activeEnv() {
        String env = System.getProperty("env");
        if (env == null || env.trim().isEmpty()) {
            env = properties.getProperty("default.env", "live");
        }
        return env.trim().toLowerCase();
    }

    /** Returns baseUrl for whichever environment is active (live or mock). */
    public String getBaseUrl() {
        String env = activeEnv();
        String key = env + ".baseUrl";
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("No baseUrl configured for env '" + env + "'. Expected key: " + key);
        }
        return value;
    }

    public String getEnv() {
        return activeEnv();
    }

    public String getDefaultPassword() {
        return properties.getProperty("default.password");
    }

    public String getIsbn1() {
        return properties.getProperty("isbn1");
    }

    public String getIsbn2() {
        return properties.getProperty("isbn2");
    }

    public String getInvalidIsbn() {
        return properties.getProperty("invalidIsbn");
    }

    public int getConnectionTimeout() {
        return Integer.parseInt(properties.getProperty("connection.timeout", "15000"));
    }

    public int getReadTimeout() {
        return Integer.parseInt(properties.getProperty("read.timeout", "15000"));
    }

    public String getExtentReportPath() {
        return properties.getProperty("extent.report.path", "reports/ExtentReport.html");
    }

    /** Generic getter for anything else added later without needing a new method. */
    public String get(String key) {
        return properties.getProperty(key);
    }
}
