package tests;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import utilities.ConfigReader;
import utilities.Driver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class T02_HomePage_FooterComponentTests {
    HomePage homePage;


    @BeforeMethod
    public void setup(){
        homePage = new HomePage(Driver.getDriver());
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }


    @Test(priority = 1)
    public void testFooterIsVisible(){
        Assert.assertTrue(homePage.footerBlock.isDisplayed());
    }

    @Test(priority = 2)
    public void testFooterMenuSectionsVisable(){

        int menuSize = homePage.footerMenuSections.size();
        int count = 0;
        for (int i = 0; i < menuSize ; i++){
            Assert.assertTrue(homePage.footerMenuSections.get(i).isDisplayed());
            System.out.println("Check: " + homePage.footerMenuSections.get(i).getAttribute("class"));
        }
    }

    @Test(priority = 3)
    public void testInformationLinksVisible() {
        int menuSize = homePage.informationLinks.size();
        int count = 0;
        for (int i = 0; i < menuSize ; i++){
            Assert.assertTrue(homePage.informationLinks.get(i).isDisplayed());
            System.out.println("check if " + homePage.informationLinks.get(i).getText() + " is checked.");
        }
    }

    @Test(priority = 4)
    public void testCustomerServiceLinksVisible() {
        int menuSize = homePage.customerServiceLinks.size();
        int count = 0;
        for (int i = 0; i < menuSize ; i++){
            Assert.assertTrue(homePage.customerServiceLinks.get(i).isDisplayed());
            System.out.println("check if " + homePage.customerServiceLinks.get(i).getText() + " is checked.");
        }
    }

    @Test(priority = 5)
    public void testAccountLinksVisible() {
        int menuSize = homePage.myAccountLinks.size();
        int count = 0;
        for (int i = 0; i < menuSize ; i++){
            Assert.assertTrue(homePage.myAccountLinks.get(i).isDisplayed());
            System.out.println("check if " + homePage.myAccountLinks.get(i).getText() + " is checked.");
        }
    }
    @Test(priority = 6)
    public void testFollowUsLinksVisible() {
        int menuSize = homePage.followUsLinks.size();
        for (int i = 0; i < menuSize ; i++){
            Assert.assertTrue(homePage.followUsLinks.get(i).isDisplayed());
            System.out.println("Checked: " + homePage.informationLinks.get(i).getText());
        }
    }




    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }
}
