package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.util.ArrayList;
import java.util.List;

public class T05_HomePage_TopMenuTest {

    HomePage homePage;
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(Driver.getDriver());
        loginPage = new LoginPage(Driver.getDriver());
        productPage = new ProductPage(Driver.getDriver());
        cartPage = new CartPage(Driver.getDriver());
        Driver.getDriver().get(ConfigReader.getProperty("url"));

    }

    @Test
    public void E01_CategoryTitleValidation_SoftAssert(){

        SoftAssert softAssert = new SoftAssert();
        int index = 1;
        for (WebElement category : homePage.categories){
            String expectedTitle = category.getText().trim();

            System.out.println(index++ + ". Kategori: " + expectedTitle);
            category.click();

            String actualTitle = productPage.categoryTitle.getText().trim();
            softAssert.assertTrue(actualTitle.equalsIgnoreCase(expectedTitle),
                    "❌ Beklenen başlık: '" + expectedTitle + "' | Görülen: '" + actualTitle + "'");


            Driver.getDriver().navigate().back();
            ReusableMethods.waitForSeconds(1);
        }
        softAssert.assertAll(); // Tüm sonuçları burada kontrol eder
    }

    @Test(priority = 2)
    public void testTopMenuOrderIsCorrect() {
        List<String> expectedOrder = List.of(
                "Books", "Computers", "Electronics",
                "Apparel & Shoes", "Digital downloads",
                "Jewelry", "Gift Cards"
        );

        List<String> actualOrder = new ArrayList<>();

        for (WebElement menu : homePage.categories) {
            actualOrder.add(menu.getText().trim());
        }

        for (int i = 0; i < expectedOrder.size(); i++) {
            String expected = expectedOrder.get(i).trim();
            String actual = actualOrder.get(i).trim();

            Assert.assertTrue(actual.equalsIgnoreCase(expected),
                    "Menü sırası beklenenden farklı!");
        }
    }

    @AfterClass
    public void tearDown() {
        Driver.closeDriver();
    }
}
