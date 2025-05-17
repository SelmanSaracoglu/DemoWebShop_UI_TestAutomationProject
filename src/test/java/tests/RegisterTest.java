package tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestDataReader;
import utilities.TestDataWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterTest {

    HomePage homePage;
    RegisterPage registerPage;
    LoginPage loginPage;
    Faker faker;
    String firstName, lastName, email, password;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(Driver.getDriver());
        registerPage = new RegisterPage(Driver.getDriver());
        loginPage = new LoginPage(Driver.getDriver());
        faker = new Faker();
        Driver.getDriver().get(ConfigReader.getProperty("url"));


    }

    @Ignore
    @Test
    public void testValidRegistrationWithFaker(){
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = faker.internet().emailAddress();
        password = faker.internet().password(8, 12, true, true, true);

        homePage.registerLink.click();
        registerPage.genderMaleRadio.click();
        registerPage.firstNameInput.sendKeys(firstName);
        registerPage.lastNameInput.sendKeys(lastName);
        registerPage.emailInput.sendKeys(email);
        registerPage.passwordInput.sendKeys(password);
        registerPage.confirmPasswordInput.sendKeys(password);
        registerPage.registerButton.click();

        Assert.assertTrue(registerPage.successMessage.isDisplayed());
        Assert.assertTrue(registerPage.successMessage.getText().contains("completed"));

        // sadece başarılı kayıttan sonra yaz!
        TestDataWriter.writeUserDataToFile(firstName, lastName, email, password);

    }

    @Test
    public void testLoginfromProperties(){

        homePage.loginLink.click();
        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");
    }

    @Test
    public void testLoginWithLastRegisteredUser(){
        // test-user-data.txt dosyasından son email ve şifreyi al
        String[] credentials = TestDataReader.readLastUserCredentials("src/test/resources/test-user-data.txt");
        String email = credentials[0];
        String password = credentials [1];

        // Login sayfasına git
        homePage.loginLink.click();

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");
    }



    @AfterMethod
    public void tearDownClass() {
        Driver.closeDriver();
    }
}
