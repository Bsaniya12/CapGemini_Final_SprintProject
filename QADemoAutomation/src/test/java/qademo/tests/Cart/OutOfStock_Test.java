
package qademo.tests.Cart;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;

public class OutOfStock_Test extends BaseClass {

    @Test(groups = {"NT"})
    public void verifyOutOfStockProduct() {

        openPage("/catalog");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated( By.cssSelector("[data-testid='product-grid']")));     
        List<WebElement> badges = driver.findElements(By.cssSelector("[data-testid^='product-out-of-stock-badge-']"));
                
        Assert.assertTrue(!badges.isEmpty(),"No out-of-stock products found.");
                

        for (WebElement badge : badges) {

            String badgeId = badge.getAttribute("data-testid");
            String productId = badgeId.replace(
                    "product-out-of-stock-badge-", "");

            WebElement card = driver.findElement(  By.cssSelector("[data-testid='product-card-" + productId + "']"));
                 
            WebElement link = card.findElement( By.cssSelector("a[data-testid^='product-link-']"));
                   
            String productName = link.getText();

            if (productName.isEmpty()) {
                productName = link.getAttribute("aria-label");
            }

            WebElement button = card.findElement(
                    By.cssSelector("button[data-testid^='product-add-to-cart-']"));

            Assert.assertFalse(button.isEnabled(),"Out-of-stock product should not be addable.");          
            Reporter.log("Out-of-stock product: " + productName,true);
        }

        Reporter.log("PASS: Out-of-stock products verified.",true);
    }
}
