package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * Single entry point for the whole suite (invoked by testng.xml / mvn test).
 *
 * plugin list produces:
 *  - pretty                         -> readable console output
 *  - html:reports/cucumber-report   -> native Cucumber HTML report
 *  - json:reports/cucumber.json     -> machine-readable report (also consumed by the Extent adapter)
 *  - junit:reports/cucumber.xml     -> CI-friendly XML
 *  - tech.grasshopper.extentreports.cucumber7.adapter.ExtentCucumberAdapter:
 *        -> generates reports/spark/ (Extent HTML report) automatically from this same run,
 *           configured via src/test/resources/extent.properties
 *
 * dataProviderThreadCount in testng.xml controls parallelism; keep it at 1 while
 * you are first stabilizing chaining-dependent scenarios (Account -> BookStore),
 * since userId/token chaining across a scenario is designed to be sequential
 * within that scenario, not across scenarios.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
            "pretty",
            "html:reports/cucumber-report/cucumber.html",
            "json:reports/cucumber-report/cucumber.json",
            "junit:reports/cucumber-report/cucumber.xml",
            "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"   // <- fixed
        },
        monochrome = true,
        publish = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @org.testng.annotations.DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
