
package qademo.tests.Cart;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.CartPage;
import qademo.objrepo.ProductPage;
import qademo.utility.ExcelUtility;

public class DecreaseQuantity extends BaseClass {

    @Test(priority = 5, groups = {"FT"})
    public void verifyDecreaseQuantity() throws Exception {

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(20));

        // Step 1: Read product name from Excel
        String productName = ExcelUtility.getDataByHeader(
                "TC-07", "ProductName");

        productName = productName.split(";")[0].trim();

        // Step 2: Open catalog
        openPage("/catalog");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid^='product-card-']")));

        // Step 3: Add product to cart
        ProductPage product = new ProductPage(driver);
        product.addProductToCart(productName);

        // Step 4: Open cart
        openPage("/cart");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='cart-page']")));

        CartPage cart = new CartPage(driver);

        // Step 5: Get initial quantity
        int initialQuantity = cart.getFirstProductQuantity();

        // Step 6: Increase quantity
        cart.increaseFirstProductQuantity();

        wait.until(d -> {
            try {
                return cart.getFirstProductQuantity()
                        == initialQuantity + 1;
            } catch (Exception e) {
                return false;
            }
        });

        int increasedQuantity = cart.getFirstProductQuantity();

        Assert.assertEquals(
                increasedQuantity,
                initialQuantity + 1,
                "Product quantity did not increase correctly.");

        // Step 7: Decrease quantity
        cart.decreaseFirstProductQuantity();

        wait.until(d -> {
            try {
                return cart.getFirstProductQuantity()
                        == increasedQuantity - 1;
            } catch (Exception e) {
                return false;
            }
        });

        int finalQuantity = cart.getFirstProductQuantity();

        // Step 8: Verify final quantity
        Assert.assertEquals(
                finalQuantity,
                initialQuantity,
                "Product quantity did not decrease correctly.");

        Reporter.log(
                "PASS: Quantity increased and decreased successfully for "
                + productName, true);
    }
}
