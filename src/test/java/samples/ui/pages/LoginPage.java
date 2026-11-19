package samples.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import samples.ui.UiTestBase;

/** The Sauce Demo login form. */
public class LoginPage {

    private static final By USERNAME = By.id("user-name");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR = By.cssSelector("[data-test='error']");

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage open() {
        driver.get(UiTestBase.BASE_URL);
        return this;
    }

    public void logIn(String username, String password) {
        driver.findElement(USERNAME).sendKeys(username);
        driver.findElement(PASSWORD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
    }

    public boolean isErrorShown() {
        return !driver.findElements(ERROR).isEmpty();
    }
}
