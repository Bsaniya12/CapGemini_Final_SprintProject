
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
import qademo.utility.ExcelUtility;

public class CartCheckoutIntegration_Test extends BaseClass {

    @Test(groups = {"IT"})
    public void verifyCartToCheckoutIntegration() throws Exception {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        // Step 1: Open login page
        openPage("/login");

        // Step 2: Log in
        LoginPage login = new LoginPage(driver);
        login.enterEmail(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickSignIn();

        wait.until(ExpectedConditions.urlContains("/catalog"));

        // Step 3: Read product name from Excel
        String productName = ExcelUtility.getDataByHeader( "TC-04", "ProductName");            
        productName = productName.split(";")[0].trim();

        // Step 4: Open catalog and add product
        openPage("/catalog");

        ProductPage product = new ProductPage(driver);
        product.addProductToCart(productName);

        // Step 5: Open cart
        openPage("/cart");

        // Step 6: Verify product in cart
        By cartProduct = By.xpath("//*[@data-testid='cart-items-list']//*[contains(normalize-space(), '"
        		 + productName + "')]");
               

        wait.until(ExpectedConditions.visibilityOfElementLocated(cartProduct));

                
        // Step 7: Click Proceed to Checkout
        By checkoutButton = By.xpath("//button[contains(normalize-space(), 'Proceed to Checkout')]");
                
        wait.until(ExpectedConditions.elementToBeClickable( checkoutButton)).click();
               

        // Step 8: Wait for checkout page
        wait.until(ExpectedConditions.urlContains("/checkout"));

        // Step 9: Verify product in checkout summary
        By checkoutProduct = By.xpath( "//*[contains(normalize-space(), '" + productName + "')]");
               
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutProduct));
                
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout"), "Checkout page did not open.");
                
        Reporter.log( "PASS: Cart product transferred to checkout successfully.",true);
               
    }
}
