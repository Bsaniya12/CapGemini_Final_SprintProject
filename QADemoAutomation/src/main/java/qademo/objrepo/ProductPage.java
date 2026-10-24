
package qademo.objrepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    WebDriver driver;
    WebDriverWait wait;

    By productGrid =
            By.cssSelector("[data-testid='product-grid']");

    By productCards =
            By.cssSelector("[data-testid^='product-card-']");

    By addButtons =
            By.cssSelector("button[data-testid^='product-add-to-cart-']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Open the product catalog
    public void clickBrowseProducts() {

        driver.get("https://qademo.com/catalog");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                productGrid));
    }

    // Verify the product page
    public boolean isProductPageDisplayed() {

        return driver.getCurrentUrl().contains("/catalog")
                && !driver.findElements(productGrid).isEmpty();
    }

    // Add the first available product
    public void clickAddToCart() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                productGrid));

        List<WebElement> buttons = driver.findElements(addButtons);

        for (WebElement button : buttons) {

            if (button.isEnabled()
                    && button.getAttribute("disabled") == null) {

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        button);

                try {
                    wait.until(ExpectedConditions.elementToBeClickable(
                            button)).click();

                } catch (ElementClickInterceptedException e) {

                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].click();", button);
                }

                System.out.println("Product added to cart.");
                return;
            }
        }

        throw new RuntimeException(
                "No available product found in the catalog.");
    }

    // Add a specific product by name
    public void addProductToCart(String productName) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                productGrid));

        // Find the exact product name
        By productNameLocator = By.xpath(
                "//*[@data-testid[starts-with(.,'product-name-')] "
                + "and normalize-space(text())='"
                + productName.trim() + "']");

        List<WebElement> names =
                driver.findElements(productNameLocator);

        if (names.isEmpty()) {
            throw new RuntimeException(
                    "Product name not found in catalog: " + productName);
        }

        // Find the product card containing the product name
        WebElement productCard = names.get(0).findElement(
                By.xpath("./ancestor::*[starts-with(@data-testid,"
                + "'product-card-')][1]"));

        // Find the Add button inside that card
        List<WebElement> buttons =
                productCard.findElements(addButtons);

        if (buttons.isEmpty()) {
            throw new RuntimeException(
                    "Add button not found for: " + productName);
        }

        WebElement button = buttons.get(0);

        if (!button.isEnabled()
                || button.getAttribute("disabled") != null) {
            throw new RuntimeException(
                    "Product is out of stock or unavailable: "
                    + productName);
        }

        // Scroll to the button
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                button);

        wait.until(ExpectedConditions.elementToBeClickable(button));

        try {
            button.click();

        } catch (ElementClickInterceptedException e) {

            wait.until(ExpectedConditions.elementToBeClickable(button));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", button);
        }

        System.out.println("Added product: " + productName);
    }

    // Get cart item count
    public String getCartCount() {

        By cartCount = By.cssSelector(
                "[data-testid='cart-count'], "
                + "[data-testid='cart-badge']");

        List<WebElement> elements =
                driver.findElements(cartCount);

        if (elements.isEmpty()) {
            return "";
        }

        return elements.get(0).getText().trim();
    }

    // Check whether products are displayed
    public boolean areProductsDisplayed() {

        return !driver.findElements(productCards).isEmpty();
    }
}
