package tests;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.demo.framework.pages.pages.CartPage;
import com.demo.framework.pages.pages.HomePage;
import com.demo.framework.pages.pages.LoginPage;
import com.demo.framework.pages.pages.ProductPage;
import com.demo.framework.core.ConfigReader;
import com.demo.framework.core.Driver;
import com.demo.framework.core.ReusableMethods;

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

    @Test(priority = 3)
    public void testHoverComputersAndCheckSubmenusVisible() {
        Actions actions = new Actions(Driver.getDriver());

        // Ana menü öğesine gel
        actions.moveToElement(homePage.categories.get(1)).perform();
        ReusableMethods.waitForSeconds(1);

        for (WebElement subLink : homePage.getComputersSubmenuLinks()) {
            Assert.assertTrue(subLink.isDisplayed(),
                    "Alt menü görünmüyor: " + subLink.getText());
            System.out.println("Alt menü görünüyor: " + subLink.getText());
        }
    }

    @Test(priority = 4)
    public void testHoverElectronicsAndCheckSubmenusVisible() {
        Actions actions = new Actions(Driver.getDriver());

        // Ana menü öğesine gel
        actions.moveToElement(homePage.categories.get(2)).perform();
        ReusableMethods.waitForSeconds(1);

        for (WebElement subLink : homePage.getElectronicsSubmenuLinks()) {
            Assert.assertTrue(subLink.isDisplayed(),
                    "Alt menü görünmüyor: " + subLink.getText());
            System.out.println("Alt menü görünüyor: " + subLink.getText());
        }
    }

    @AfterClass
    public void tearDown() {
        Driver.closeDriver();
    }
}
