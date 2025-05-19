package tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

public class T04_HomePage_MainSliderTest {
    HomePage homePage;

    @BeforeMethod
    public void setUp() {
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        homePage = new HomePage(Driver.getDriver());
    }

    @Test(priority = 1)
    public void testSliderIsVisible() {

        Assert.assertTrue(homePage.mainSlider.isDisplayed(),"Slider görünmüyor!");
    }

    @Test(priority = 2)
    public void testSliderNavigationWithArrows() {


        ReusableMethods.waitForSeconds(2);
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(homePage.mainSlider).perform();
        Assert.assertTrue(homePage.sliderLeftArrow.isDisplayed());
        Assert.assertTrue(homePage.sliderRightArrow.isDisplayed());

        homePage.sliderRightArrow.click();
        ReusableMethods.waitForSeconds(2);

        homePage.sliderLeftArrow.click();
        ReusableMethods.waitForSeconds(2);

    }



    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }
}
