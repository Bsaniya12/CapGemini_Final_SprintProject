
package qademo.objrepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {

    WebDriver driver;

    @FindBy(id = "firstName")
    WebElement firstName;

    @FindBy(id = "lastName")
    WebElement lastName;

    @FindBy(id = "address")
    WebElement address;

    @FindBy(id = "cardNumber")
    WebElement cardNumber;

    @FindBy(id = "expiry")
    WebElement expiry;

    @FindBy(id = "cvv")
    WebElement cvv;

    // Inspect the Name on Card field to confirm its ID
    @FindBy(id = "nameOnCard")
    WebElement nameOnCard;

    @FindBy(css = "[data-testid='place-order-button']")
    WebElement placeOrder;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterFirstName(String name) {
        firstName.sendKeys(name);
    }

    public void enterLastName(String name) {
        lastName.sendKeys(name);
    }

    public void enterAddress(String value) {
        address.sendKeys(value);
    }

    public void enterCardNumber(String number) {
        cardNumber.sendKeys(number);
    }

    public void enterExpiry(String date) {
        expiry.sendKeys(date);
    }

    public void enterCvv(String value) {
        cvv.sendKeys(value);
    }

    public void enterNameOnCard(String name) {
        nameOnCard.sendKeys(name);
    }

    public void clickPlaceOrder() {
        placeOrder.click();
    }
}
