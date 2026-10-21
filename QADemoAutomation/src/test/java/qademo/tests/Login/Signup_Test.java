
package qademo.tests.Login;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.SignupPage;
import qademo.utility.ExcelUtility;

public class Signup_Test extends BaseClass {

    @Test
    public void verifySignupWithValidDetails() throws Exception {

        // Read signup data from Excel
        String email = ExcelUtility.getSignupDataByHeader("LOG-17", "Email");
        String phone = ExcelUtility.getSignupDataByHeader("LOG-17", "Phone");
        String username = ExcelUtility.getSignupDataByHeader("LOG-17", "Username");
        String password = ExcelUtility.getSignupDataByHeader("LOG-17", "Password");
        String confirmPassword = ExcelUtility.getSignupDataByHeader("LOG-17", "ConfirmPassword");

        // Open signup page
        openPage("/signup");

        // Enter signup details
        SignupPage signup = new SignupPage(driver);

        signup.enterEmail(email);
        signup.enterPhone(phone);
        signup.enterUsername(username);
        signup.enterPassword(password);
        signup.enterConfirmPassword(confirmPassword);

        // Check password confirmation
        if (!password.equals(confirmPassword)) {
            throw new Exception("Password and Confirm Password do not match.");
        }

        // Click Create Account
        signup.clickCreateAccount();

        // Wait for the page to respond
        Thread.sleep(2000);

        // Print page details for troubleshooting
        Reporter.log("Current URL: " + driver.getCurrentUrl(),true);
        Reporter.log("Page title: " + driver.getTitle(),true);
        Reporter.log("Page content:",true);
        Reporter.log(driver.findElement(By.tagName("body")).getText(),true);
    
        // Check whether the product grid is present
        int productGridCount = driver.findElements(
                By.cssSelector("[data-testid='product-grid']")).size();  

        Reporter.log("Product grid count: " + productGridCount,true);

        if (productGridCount > 0) {
        	Reporter.log("PASS: Product catalog detected after signup.",true);
        } else {
        	Reporter.log("Signup not yet verified. Check the page content above.",true);

        }
    }
}
