# QA Engineering Samples (Java)

[![tests](https://github.com/StanUser-Work/qa-engineering-samples-java/actions/workflows/tests.yml/badge.svg)](https://github.com/StanUser-Work/qa-engineering-samples-java/actions/workflows/tests.yml)

**4 automated tests in Java 21**: 2 UI tests (Selenium WebDriver) and 2 API tests (REST Assured), run by JUnit 5 and Maven. Each test checks expected results.

This is the Java version of my C# repo [qa-engineering-samples](https://github.com/StanUser-Work/qa-engineering-samples), which also has a manual QA example (bug report and test cases).

| Part | Target | Tools |
|---|---|---|
| UI tests | [Sauce Demo](https://www.saucedemo.com), a public demo shop made for test automation | JUnit 5, Selenium WebDriver 4, Page Object pattern |
| API tests | [Restful-Booker](https://restful-booker.herokuapp.com/apidoc), a public demo REST API for hotel bookings | JUnit 5, REST Assured, Jackson |

## Tests

| # | Type | Test | What it checks |
|---|---|---|---|
| 1 | UI | [`validUser_logsIn_andSeesProductCatalog`](src/test/java/samples/ui/tests/LoginTests.java) | Valid login opens the catalog with 6 products and no error |
| 2 | UI | [`addProductToCart_showsProductInCart_withCorrectNameAndPrice`](src/test/java/samples/ui/tests/CartTests.java) | An added product is in the cart with the right name, price and quantity |
| 3 | API | [`createBooking_thenGetById_returnsSameData`](src/test/java/samples/api/tests/BookingApiTests.java) | A created booking is stored exactly as sent |
| 4 | API | [`deleteBooking_withValidToken_removesBooking`](src/test/java/samples/api/tests/BookingApiTests.java) | An authorized delete removes the booking (then `GET` returns 404) |

## Code structure

| Path | Contents |
|---|---|
| [`src/test/java/samples/ui/UiTestBase.java`](src/test/java/samples/ui/UiTestBase.java) | Headless Chrome setup, screenshot saved to `target/screenshots/` on failure |
| [`src/test/java/samples/ui/pages/`](src/test/java/samples/ui/pages/) | Page Objects: `LoginPage`, `InventoryPage`, `CartPage` |
| [`src/test/java/samples/api/BookingApiClient.java`](src/test/java/samples/api/BookingApiClient.java) | REST Assured client for the booking endpoints |
| [`src/test/java/samples/api/models/`](src/test/java/samples/api/models/) | Java records for request and response bodies |
| [`.github/workflows/tests.yml`](.github/workflows/tests.yml) | GitHub Actions: runs all tests on every push |

API tests delete the bookings they create, so the shared demo API is left clean.

## How to run

You need **JDK 21+**, **Maven 3.9+** and **Google Chrome**. Selenium Manager downloads the matching ChromeDriver by itself.

```bash
git clone https://github.com/StanUser-Work/qa-engineering-samples-java.git
cd qa-engineering-samples-java
mvn test                     # all 4 tests
mvn test -Dgroups=api        # only API tests
mvn test -Dgroups=ui         # only UI tests
mvn test -Dheadless=false    # watch the browser
```

Reports are written to `target/surefire-reports/`.
