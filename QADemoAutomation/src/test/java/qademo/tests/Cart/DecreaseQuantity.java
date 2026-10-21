
package qademo.tests.Cart;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
               

        // Step 1: Read product name from Excel
        String productName = ExcelUtility.getDataByHeader("TC-07", "ProductName");
                
        // Step 2: Open catalog
        openPage("/catalog");

        wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                .presenceOfElementLocated( By.cssSelector("[data-testid^='product-card-']")));
                       

        // Step 3: Add product to cart
        ProductPage product = new ProductPage(driver);
        product.addProductToCart(productName.trim());

        // Step 4: Open cart
        openPage("/cart");
        
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='cart-page']")));

        CartPage cart = new CartPage(driver);

        // Step 5: Get initial quantity
        int initialQuantity = cart.getFirstProductQuantity();

        // Step 6: Increase quantity
        cart.increaseFirstProductQuantity();
        wait.until(d -> cart.getFirstProductQuantity()== initialQuantity + 1);
                
        int increasedQuantity = cart.getFirstProductQuantity();

        Assert.assertEquals( increasedQuantity,initialQuantity + 1,"Product quantity did not increase correctly.");
          
        // Step 7: Decrease quantity
        cart.decreaseFirstProductQuantity();

        wait.until(d -> cart.getFirstProductQuantity() == initialQuantity);
             int finalQuantity = cart.getFirstProductQuantity();

        // Step 8: Verify final quantity
        Assert.assertEquals(finalQuantity,initialQuantity,"Product quantity did not decrease correctly.");
        Reporter.log("PASS: Quantity increased and decreased successfully for " + productName, true);
                               
    }
}
