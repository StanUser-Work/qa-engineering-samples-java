package samples.ui;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Shared setup: headless Chrome, a 1280x800 window and a screenshot when a test fails. */
public abstract class UiTestBase {

    public static final String BASE_URL = "https://www.saucedemo.com";

    protected WebDriver driver;
    protected WebDriverWait wait;

    @RegisterExtension
    final TestWatcher screenshotOnFailure = new TestWatcher() {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            saveScreenshot(context.getDisplayName());
            quitDriver();
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            quitDriver();
        }

        @Override
        public void testAborted(ExtensionContext context, Throwable cause) {
            quitDriver();
        }

        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {
            quitDriver();
        }
    };

    @BeforeEach
    void startBrowser() {
        ChromeOptions options = new ChromeOptions();
        if (!"false".equalsIgnoreCase(System.getProperty("headless"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1280,800", "--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void saveScreenshot(String testName) {
        if (driver == null) {
            return;
        }
        try {
            Path dir = Path.of("target", "screenshots");
            Files.createDirectories(dir);
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Files.write(dir.resolve(testName.replaceAll("[^A-Za-z0-9_-]", "_") + ".png"), png);
        } catch (Exception e) {
            System.err.println("Could not save screenshot: " + e.getMessage());
        }
    }

    private void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
