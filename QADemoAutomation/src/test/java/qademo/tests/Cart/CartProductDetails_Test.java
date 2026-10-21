
package qademo.tests.Cart;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.ProductPage;
import qademo.utility.ExcelUtility;

public class CartProductDetails_Test extends BaseClass {

    @Test(priority = 3, groups = {"FT"})
    public void verifyCartProductDetails() throws Exception {

        // Read product name from Excel
        String productName = ExcelUtility.getDataByHeader( "TC-04", "ProductName");
               

        // Open catalog
        openPage("/catalog");

        // Add selected product
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart(productName);

        // Open cart
        openPage("/cart");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
   
        // Find the cart item
        WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated( By.cssSelector(
        		"[data-testid='cart-items-list'] > *"))) ;
                                
        // Verify product details
        String actualDetails = cartItem.getText();

        Assert.assertTrue( actualDetails.contains(productName),"Product name is missing from the cart.");  
        Assert.assertTrue(actualDetails.contains("$"), "Product price is missing from the cart.");
        Reporter.log("PASS: Product details verified for " + productName,true);
       
    }
}
