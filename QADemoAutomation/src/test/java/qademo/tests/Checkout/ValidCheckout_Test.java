
package qademo.tests.Checkout;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.LoginPage;
import qademo.objrepo.ProductPage;
import qademo.objrepo.CartPage;
import qademo.utility.ExcelUtility;

public class ValidCheckout_Test extends BaseClass {

    @Test(groups = {"FT"})
    public void verifyValidCheckout() throws Exception {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(20));

        // Step 1: Login
        openPage("/login");

        LoginPage login = new LoginPage(driver);
        login.enterEmail(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickSignIn();

        wait.until(ExpectedConditions.urlContains("/catalog"));

        // Step 2: Read checkout data from Excel
        String id = "TC-CHK-01";

        String firstName =
                ExcelUtility.getCheckoutDataByHeader(id, "FirstName");

        String lastName =
                ExcelUtility.getCheckoutDataByHeader(id, "LastName");

        String address =
                ExcelUtility.getCheckoutDataByHeader(id, "Address");

        String card =
                ExcelUtility.getCheckoutDataByHeader(id, "CardNumber");

        String expiry =
                ExcelUtility.getCheckoutDataByHeader(id, "ExpiryDate");

        String cvv =
                ExcelUtility.getCheckoutDataByHeader(id, "CVV");

        String name =
                ExcelUtility.getCheckoutDataByHeader(id, "NameOnCard");

        // Step 3: Open catalog
        openPage("/catalog");

        ProductPage page = new ProductPage(driver);

        // Step 4: Add product to cart
        page.addProductToCart("Laptop Backpack");

        // Step 5: Open cart
        openPage("/cart");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='cart-page']")));

        // Step 6: Verify cart contains a product
        By cartItems =
                By.cssSelector("[data-testid^='cart-item-']");

        wait.until(ExpectedConditions.presenceOfElementLocated(cartItems));

        int itemCount = driver.findElements(cartItems).size();

        System.out.println("Cart URL: " + driver.getCurrentUrl());
        System.out.println("Cart items: " + itemCount);

        Assert.assertTrue(
                itemCount > 0,
                "Cart is empty. Product was not added.");

        // Step 7: Proceed to checkout
        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();

        // Step 8: Wait for checkout form
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("firstName")));

        // Step 9: Enter checkout details
        driver.findElement(By.id("firstName")).sendKeys(firstName);
        driver.findElement(By.id("lastName")).sendKeys(lastName);
        driver.findElement(By.id("address")).sendKeys(address);
        driver.findElement(By.id("cardNumber")).sendKeys(card);
        driver.findElement(By.id("expiry")).sendKeys(expiry);
        driver.findElement(By.id("cvv")).sendKeys(cvv);
        driver.findElement(By.id("nameOnCard")).sendKeys(name);

        // Step 10: Verify checkout page
        Assert.assertTrue(
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("[data-testid='checkout-page']")))
                        .isDisplayed(),
                "Checkout page is not displayed");

        Reporter.log(
                "PASS: Checkout details entered for " + id, true);
    }
}
