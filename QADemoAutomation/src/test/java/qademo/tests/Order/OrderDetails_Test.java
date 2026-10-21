package qademo.tests.Order;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.LoginPage;

public class OrderDetails_Test extends BaseClass {

    @Test(groups = {"FT"})
    public void verifyOrderDetails() {

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15));

        // Login
        openPage("/login");

        LoginPage login = new LoginPage(driver);
        login.enterEmail(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickSignIn();

        wait.until(ExpectedConditions.urlContains("/catalog"));

        // Open Order History
        openPage("/orders");

        wait.until(ExpectedConditions.urlContains("/orders"));

        // Wait for order information to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));         

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Read and print order page information
        String pageText = driver.findElement(By.tagName("body")).getText();              
        Reporter.log("Current URL: " + driver.getCurrentUrl(),true);
        Reporter.log("Order page content:",true);
        Reporter.log(pageText,true);
    }
}