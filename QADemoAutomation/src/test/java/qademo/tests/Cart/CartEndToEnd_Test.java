
package qademo.tests.Cart;

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

public class CartEndToEnd_Test extends BaseClass {

    @Test(groups = {"ST"})
    public void verifyEndToEndCartFlow() throws Exception {

        WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(15));
                
        // Login
        openPage("/login");

        LoginPage login = new LoginPage(driver);
        login.enterEmail(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickSignIn();

        wait.until(ExpectedConditions.urlContains("/catalog"));

        // Add product
        String productName =ExcelUtility.getDataByHeader("TC-04", "ProductName");
                
        productName = productName.split(";")[0].trim();

        openPage("/catalog");

        ProductPage product = new ProductPage(driver);
        product.addProductToCart(productName);

        // Open cart and verify product
        openPage("/cart");

        By cartProduct = By.xpath( "//*[@data-testid='cart-items-list']" + "//*[contains(normalize-space(), '" + productName + "')]");
        wait.until( ExpectedConditions.visibilityOfElementLocated(cartProduct));
       
        Assert.assertTrue(
                driver.findElement(cartProduct).isDisplayed(), "Product was not displayed in the cart.");
        
        // Proceed to checkout
        By checkoutButton = By.xpath( "//button[contains(normalize-space(), 'Proceed to Checkout')]");
  

        wait.until( ExpectedConditions.elementToBeClickable(checkoutButton)).click();
     
        wait.until(ExpectedConditions.urlContains("/checkout"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout"),"Checkout page did not open.");
        Reporter.log("PASS: End-to-end cart flow completed successfully.",true);
    }  
}
