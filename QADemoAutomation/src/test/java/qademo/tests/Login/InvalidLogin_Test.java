
package qademo.tests.Login;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.LoginPage;
import qademo.utility.ExcelUtility;

public class InvalidLogin_Test extends BaseClass {

    @Test(groups = {"NT"})
    public void verifyLoginWithIncorrectPassword() throws Exception {

        String username = ExcelUtility.getInvalidLoginDataByHeader( "LOG-15", "Username");
        String password = ExcelUtility.getInvalidLoginDataByHeader("LOG-15", "Password");
                

        // Open login page
        openPage("/login");

        // Enter login details
        LoginPage login = new LoginPage(driver);
        login.enterEmail(username);
        login.enterPassword(password);
        login.clickSignIn();

        // Wait for login response
        WebDriverWait wait = new WebDriverWait( driver, java.time.Duration.ofSeconds(15));

               
        wait.until(d -> {
            return !d.findElements(By.cssSelector("[role='alert']")).isEmpty();
        });

        // Check error message
        boolean errorDisplayed = false;

        for (WebElement element :  driver.findElements(By.cssSelector("[role='alert']"))) {
              

            if (element.isDisplayed()
                    && !element.getText().trim().isEmpty()) {

                System.out.println("Login error: " + element.getText());
                errorDisplayed = true;
                break;
            }
        }

        Assert.assertTrue(errorDisplayed,"Login error message was not displayed.");
                

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "User should remain on the login page.");
               

        // Take screenshot
        Path folder = Paths.get("screenshots");
        Files.createDirectories(folder);
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path destination = folder.resolve("LOG-15_InvalidLogin.png");
        Files.copy(screenshot.toPath(), destination,StandardCopyOption.REPLACE_EXISTING);
        
        Reporter.log("Screenshot saved: "+ destination.toAbsolutePath(),true);
        Reporter.log("PASS: Incorrect password was rejected.",true);
    }
}
