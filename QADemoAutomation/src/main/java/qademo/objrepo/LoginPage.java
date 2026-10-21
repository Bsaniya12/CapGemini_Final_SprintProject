
package qademo.objrepo;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "username")
    WebElement email;

    @FindBy(id = "password")
    WebElement password;

    @FindBy(css = "[data-testid='login-submit-button']")
    WebElement signInButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void enterEmail(String userEmail) {
        wait.until(ExpectedConditions.visibilityOf(email));
        email.clear();
        email.sendKeys(userEmail);
    }

    public void enterPassword(String userPassword) {
        wait.until(ExpectedConditions.visibilityOf(password));
        password.clear();
        password.sendKeys(userPassword);
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.visibilityOf(signInButton));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                signInButton);

        wait.until(ExpectedConditions.elementToBeClickable(signInButton));

        try {
            signInButton.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", signInButton);
        }
    }
}
