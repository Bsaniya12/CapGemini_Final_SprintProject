
package qademo.tests.Cart;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.LoginPage;
import qademo.objrepo.ProductPage;
import qademo.utility.ExcelUtility;

public class MultipleProductsTotal_Test extends BaseClass {

    @Test(groups = {"ST"})
    public void verifyMultipleProductsTotal() throws Exception {

        WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(15));
                

        // Step 1: Open login page
        openPage("/login");

        // Step 2: Wait for username field
        wait.until(ExpectedConditions.visibilityOfElementLocated( By.id("username")));
               
        // Step 3: Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickSignIn();

        // Step 4: Wait for catalog
        wait.until(ExpectedConditions.urlContains("/catalog"));

        // Step 5: Read product names from Excel
        String[] products = ExcelUtility .getDataByHeader("TC-05", "ProductName").split(";");
               
        // Step 6: Add products to cart
        ProductPage page = new ProductPage(driver);

        for (String name : products) {
            page.addProductToCart(name.trim());
        }

        // Step 7: Open cart
        openPage("/cart");

        // Step 8: Wait for cart page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='cart-page']")));

                
        // Step 9: Verify three products are in cart
        int count = driver.findElements( By.cssSelector("[data-testid^='cart-item-'][role='listitem']")) .size();
              
        Assert.assertEquals(count, 3,"Three products should be in the cart.");

        // Step 10: Read subtotal
        String subtotal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='order-subtotal']"))).getText();
  
        // Step 11: Verify subtotal is displayed
        Assert.assertFalse(subtotal.isEmpty(),"Subtotal is missing.");               
        Reporter.log("PASS: Three products added.",true);
        Reporter.log("Subtotal: " + subtotal,true);
    }
}
