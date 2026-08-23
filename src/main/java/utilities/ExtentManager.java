package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.ConfigReader;

/**
 * Standalone Extent Reports manager (separate from the Cucumber report).
 *
 * Note: the extentreports-cucumber7-adapter dependency in pom.xml already
 * auto-generates a full Extent report from Cucumber's run (configured via
 * extent.properties, see src/test/resources) -- that is the primary report
 * satisfying "cucumber report AND extent report" side by side.
 *
 * This ExtentManager class is kept as a reusable component in case you want
 * a SECOND custom Extent report (e.g. for non-Cucumber/manual API smoke
 * checks), or to log extra evidence into Hooks without touching Cucumber's
 * own scenario object. It is intentionally decoupled and optional.
 */
public class ExtentManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String path = ConfigReader.getInstance().getExtentReportPath();
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(path);
            sparkReporter.config().setDocumentTitle("BookStore API Test Report");
            sparkReporter.config().setReportName("RestAssured BDD - Extent Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Environment", ConfigReader.getInstance().getEnv());
            extent.setSystemInfo("Base URL", ConfigReader.getInstance().getBaseUrl());
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        return getInstance().createTest(testName);
    }

    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
