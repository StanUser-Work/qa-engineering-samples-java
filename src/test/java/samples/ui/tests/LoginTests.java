package samples.ui.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import samples.ui.UiTestBase;
import samples.ui.pages.InventoryPage;
import samples.ui.pages.LoginPage;
import samples.ui.testdata.Users;

@Tag("ui")
class LoginTests extends UiTestBase {

    @Test
    @DisplayName("A user with valid credentials logs in and lands on the product catalog")
    void validUser_logsIn_andSeesProductCatalog() {
        LoginPage loginPage = new LoginPage(driver).open();
        InventoryPage inventoryPage = new InventoryPage(driver);

        loginPage.logIn(Users.STANDARD_USER, Users.PASSWORD);

        // Expected: the user is redirected to the catalog and sees all 6 products.
        wait.until(ExpectedConditions.urlMatches("/inventory\\.html$"));
        assertAll(
                () -> assertEquals("Products", inventoryPage.title(), "page title"),
                () -> assertEquals(6, inventoryPage.products().size(), "number of products"),
                () -> assertFalse(loginPage.isErrorShown(), "no login error is shown"));
    }
}
