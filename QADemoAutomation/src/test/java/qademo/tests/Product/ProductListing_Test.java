
package qademo.tests.Product;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.ProductPage;

public class ProductListing_Test extends BaseClass {

    @Test(groups = {"SM"})
    public void verifyProductListing() {

        // Step 1: Open product page
        ProductPage product = new ProductPage(driver);
        product.clickBrowseProducts();

        // Step 2: Verify product listing is displayed
        Assert.assertTrue( product.isProductPageDisplayed(), "Product listing page is not displayed.");               
        Reporter.log( "PASS: Product listing page displayed successfully.",true);
               
    }
}
