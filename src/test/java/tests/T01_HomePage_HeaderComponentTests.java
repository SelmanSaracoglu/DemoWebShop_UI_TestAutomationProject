package tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import utilities.ConfigReader;
import utilities.Driver;

public class T01_HomePage_HeaderComponentTests {

    HomePage homePage;


    @BeforeMethod
    public void setup(){
        homePage = new HomePage(Driver.getDriver());
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }

    @Test(priority = 1)
    public void testTitle(){
        String expectedTitle = "Demo Web Shop";
        String actualTitle = Driver.getDriver().getTitle();

        Assert.assertEquals(expectedTitle,actualTitle);
    }

    @Test(priority = 2)
    public void testLogoIsDisplayed(){
        Assert.assertTrue(homePage.homePageLogo.isDisplayed());
    }

    @Test(priority = 3)
    public void testHeaderLinksAreVisible() {
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(homePage.registerLink.isDisplayed(), "Register link görünmüyor!");
        softAssert.assertTrue(homePage.loginLink.isDisplayed(), "Login link görünmüyor!");
        softAssert.assertTrue(homePage.shoppingCartLink.isDisplayed(), "Cart link görünmüyor!");
        softAssert.assertTrue(homePage.wishListLink.isDisplayed(), "Wishlist link görünmüyor!");

        softAssert.assertAll(); // Tüm hataları burada fırlatır
    }

    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }
}
