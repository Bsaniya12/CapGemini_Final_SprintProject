
package qademo.tests.Cart;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import qademo.baseClass.BaseClass;
import qademo.objrepo.ProductPage;
import qademo.utility.ExcelUtility;

public class AddMultipleProducts_Test extends BaseClass {

    @Test(groups = {"FT", "SM"})
    public void verifyMultipleProductsCanBeAdded() throws Exception {

        // Step 1: Read product names from Excel
        String products =
                ExcelUtility.getDataByHeader("TC-05", "ProductName");

        System.out.println("Products from Excel: " + products);

        // Step 2: Open product catalog
        openPage("/catalog");

        ProductPage productPage = new ProductPage(driver);

        // Step 3: Add each product
        String[] productNames = products.split(";");

        Assert.assertTrue(productNames.length >= 2,
                "Excel should contain at least two products.");

        for (String productName : productNames) {

            String product = productName.trim();

            System.out.println("Adding product: " + product);

            productPage.addProductToCart(product);
        }

        Reporter.log("Products added: " + products, true);
        Reporter.log("Multiple products test completed.", true);
    }
}
