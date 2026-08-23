package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.TestContext;

/**
 * Cucumber lifecycle hooks. TestContext is constructor-injected (PicoContainer,
 * Cucumber's default DI) into Hooks AND every StepDefinitions class, so all of
 * them share the exact same instance for the duration of one scenario, then it
 * is thrown away -- giving clean, isolated request chaining per scenario with
 * zero static/global mutable state.
 */
public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("=== Starting scenario: " + scenario.getName() + " ===");
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed() && context.getLastResponse() != null) {
            // Attach the last API response body to the Cucumber report on failure,
            // which the extentreports-cucumber7-adapter also picks up automatically.
            scenario.attach(
                    context.getLastResponse().getBody().asPrettyString(),
                    "application/json",
                    "Last API Response"
            );
        }
        System.out.println("=== Finished scenario: " + scenario.getName() + " | Status: " + scenario.getStatus() + " ===");
    }
}
