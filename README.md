# RestAssuredBDDFramework - ToolsQA BookStore API

RestAssured + Cucumber (BDD) + TestNG framework for the ToolsQA BookStore API,
covering both the **Account** and **BookStore** modules.

## What's implemented against your requirements

| Requirement | Where |
|---|---|
| No hardcoded values | `config.properties` + `ConfigReader` (URLs/ISBNs) + `TestContext` (runtime-chained token/userId/username) + `RandomDataGenerator` (unique creds per run) |
| Separate feature file per CRUD operation | `src/test/resources/features/account/*.feature` and `.../bookstore/*.feature` - one file per Create/Read/Update/Delete operation, not one giant Account.feature/BookStore.feature |
| Cucumber report | `reports/cucumber-report/cucumber.html` (native HTML) + `.json` + `.xml`, generated automatically by `TestRunner`'s `@CucumberOptions(plugin = ...)` |
| Extent report | `reports/spark/ExtentSparkReport.html`, auto-generated from the same Cucumber run via the `extentreports-cucumber7-adapter` dependency + `extent.properties` |
| JSON schema validation | `utilities/JsonSchemaValidatorUtil.java` + schemas in `src/test/resources/testdata/schemas/*.json` |
| Request chaining | `utilities/TestContext.java`, injected via Cucumber's PicoContainer into `Hooks` and both StepDefinitions classes - token/userId flow from Account steps into BookStore steps within a scenario |
| Reusable components | `api/BaseApi.java` (shared RequestSpecification), `api/AccountApi.java` / `api/BookStoreApi.java` (one method per endpoint, called from any feature), `payloads/Payloads.java` (shared body builders) |

## Prerequisites

- JDK 11+
- Maven 3.6+
- Internet access to Maven Central (to download RestAssured/Cucumber/TestNG/Extent dependencies on first build)

## One-time setup

Nothing required — `live.baseUrl` in `src/main/resources/config.properties` is already
set to `https://bookstore.toolsqa.com`. Just run `mvn clean test`.

## Running the suite

```bash
# Runs against the LIVE API (config.properties: default.env=live, live.baseUrl already set)
mvn clean test
```

## Running a subset by tag

Every scenario is tagged (`@Positive`, `@Negative`, `@Edge`, `@Boundary`, `@Smoke`,
`@Chaining`, `@Account`, `@BookStore`, plus `@Create`/`@Read`/`@Update`/`@Delete`).

```bash
mvn clean test -Dcucumber.filter.tags="@Smoke"
mvn clean test -Dcucumber.filter.tags="@BookStore and @Negative"
```

## Where reports land after a run

```
reports/
├── cucumber-report/
│   ├── cucumber.html      <- native Cucumber HTML report
│   ├── cucumber.json
│   └── cucumber.xml
└── spark/
    └── ExtentSparkReport.html   <- Extent report (open this one for the polished dashboard)
```

## Extending the framework

- **New endpoint**: add the path to `endpoints/Routes.java`, add a method to the
  matching `api/*Api.java` class, add a body builder to `payloads/Payloads.java`
  if it needs a request body.
- **New feature file**: one file per CRUD operation, saved under
  `src/test/resources/features/<module>/`. Reuse existing step phrasing where
  possible so you don't duplicate step definitions.
- **New schema**: drop a `.json` file into `src/test/resources/testdata/schemas/`
  and reference it from a feature file step: `the response should match the "your-schema.json" schema`.
