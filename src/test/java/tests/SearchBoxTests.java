package tests;

import com.beust.ah.A;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;

import java.util.NoSuchElementException;

public class SearchBoxTests {

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

    @DataProvider(name = "searchKeywords")
    public Object[][] provideSearchData(){
        return new Object[][]{
                {"book"},
                {"laptop"},
                {"camera"},
                {"gift"},
        };
    }

    @DataProvider(name = "searchMartialKeywords")
    public Object[][] provideMartialSearchData(){
        return new Object[][]{
                {"bo"},
                {"lap"},
                {"cam"},
                {"gi"},
        };
    }


    @Test
    public void E01_SearchBox_positiveTest() {

        homePage.loginLink.click();
        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        homePage.searchBox.sendKeys("book" + Keys.ENTER);

        Assert.assertTrue(ReusableMethods.isListNotEmpty(productPage.productList)
                , "Arama sonuçları bulunamadı!");
    }

    @Test
    public void E02_SearchBox_NegativeTest() {
        homePage.loginLink.click();
        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        homePage.searchBox.sendKeys("selman" + Keys.ENTER);
        Assert.assertTrue(productPage.noResultFound.isDisplayed()
                , "Negatif arama mesajı görünmedi!");
    }

    @Test(dataProvider = "searchKeywords")
    public void E03_SearchBoxWithKeywords(String keyword){
        homePage.loginLink.click();
        String email = ConfigReader.getProperty("Email");
        String password = ConfigReader.getProperty("Password");

        loginPage.emailInput.sendKeys(email);
        loginPage.passwordInput.sendKeys(password);
        loginPage.loginButton.click();

        Assert.assertTrue(homePage.logoutLink.isDisplayed(), "Login başarısız!");

        homePage.searchBox.sendKeys(keyword + Keys.ENTER);
        ReusableMethods.waitForSeconds(1);


        System.out.println("Aranan Kelime: " + keyword);

        Assert.assertTrue(ReusableMethods.isListNotEmpty(productPage.productList)
                , "Arama sonucu bulunamadı → " + keyword);

    }

    @Test
    public void E04_SearchBox_EmptyInput(){
        homePage.searchBox.sendKeys("" + Keys.ENTER);
        ReusableMethods.waitForSeconds(1);

        try {
            Alert alert= Driver.getDriver().switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Pop-up mesajı: " + alertText);

            Assert.assertTrue(alertText.toLowerCase().contains("enter"));
            alert.accept();
        } catch (NoAlertPresentException e) {
            Assert.fail("Pop-up bekleniyordu ama görünmedi.");
        }
    }

    @Test
    public void E05_SearchBox_CaseInsensitiveTest() {

        homePage.searchBox.sendKeys("BOOK" + Keys.ENTER);

        Assert.assertTrue(ReusableMethods.isListNotEmpty(productPage.productList)
                , "BÜYÜK harfle yapılan aramada sonuç gelmedi!");
    }

    @Test(dataProvider = "searchMartialKeywords")
    public void E06_SearchBox_PartialMatchTest(String partialKeyword){

        SoftAssert softAssert = new SoftAssert();
        homePage.searchBox.sendKeys(partialKeyword + Keys.ENTER);
        ReusableMethods.waitForSeconds(1);
        System.out.println("Aranan Kelime: " + partialKeyword);

        if (partialKeyword.length() < 3) {
            System.out.println(" 3 karakterden kısa arama yapıldı, uyarı bekleniyor...");
            softAssert.assertTrue(productPage.minLengthWarningMessage.isDisplayed(),
                    "Uyarı mesajı görünmedi → " + partialKeyword);
            softAssert.assertTrue(productPage.minLengthWarningMessage.getText().contains("3"),
                    "Uyarı mesajı içeriği hatalı → " + partialKeyword);
        } else {
            softAssert.assertTrue(ReusableMethods.isListNotEmpty(productPage.productList),
                    "Arama sonucu bulunamadı → " + partialKeyword);
        }

        softAssert.assertAll();

    }

    @Test
    public void E07_SearchBox_TrimmedKeywordTest() {
        SoftAssert softAssert = new SoftAssert();
        String baseKeyword = "book";

        String[] variations = {"book",
                " book",
                " book ",
                "book "
        };

        for (String keyword : variations) {
            homePage.searchBox.clear();
            homePage.searchBox.sendKeys(keyword + Keys.ENTER);
            ReusableMethods.waitForSeconds(1);

            System.out.println("Aranan Kelime: " + keyword);

            softAssert.assertTrue(ReusableMethods.isListNotEmpty(productPage.productList),
                    "Boşluklu aramada sonuç bulunamadı");
        }
        softAssert.assertAll();
    }

    @Test
    public void E08_SearchBox_SpecialCharactersTest(){
        String[] specialKeywords = {
                "book!",      // sonuna ! eklenmiş
                "lap*top",    // * karakteri
                "@camera",    // başında @
                "#gift",      // #
                "^^laptop",   // özel karakterle başlayan
                "???"         // sadece özel karakter
        };

        for (String keyword : specialKeywords){
            homePage.searchBox.clear();
            homePage.searchBox.sendKeys(keyword + Keys.ENTER);
            ReusableMethods.waitForSeconds(1);

            System.out.println("Ozel karakterli arama: " + keyword);
            String expectedResult = "No products were found";

            try {
                String actualResult = productPage.noResultFound.getText();
                Assert.assertTrue(actualResult.contains("No products were found"),
                        "Beklenen mesaj görünmedi → " + keyword);
            } catch (NoSuchElementException e) {
                Assert.fail("No result mesajı bulunamadı → " + keyword);
            }

        }
    }

    @Test
    public void E09_SearchBox_RepeatedSearchTest() {
        String keyword = "book";
        int repeatCount = 3;
        int[] resultCounts = new int[repeatCount];

        for (int i = 0; i < repeatCount ; i++) {
            homePage.searchBox.clear();
            homePage.searchBox.sendKeys(keyword + Keys.ENTER);
            ReusableMethods.waitForSeconds(1);

            int productCount = productPage.productList.size();
            resultCounts[i] = productCount;

            System.out.println(i+1 + ". arama sonucu: " + productCount + "urun bulundu.");
        }

        Assert.assertEquals(resultCounts[0], resultCounts[1], resultCounts[2],
                "Tekrarlanan aramalarda ürün sayıları eşleşmiyor!");
    }

    @Test
    public void E10_SearchBox_KeywordInProductTitleTest() {
        String keyword = "book";

        homePage.searchBox.clear();
        homePage.searchBox.sendKeys(keyword + Keys.ENTER);
        ReusableMethods.waitForSeconds(1);

        Assert.assertTrue(ReusableMethods.isListNotEmpty(productPage.productList));

        for (WebElement product : productPage.productTitles){
            String title = product.getText().toLowerCase();
            Assert.assertTrue(title.contains(keyword.toLowerCase()), "Ürün başlığı aranan kelimeyi içermiyor");
        }

    }


    @AfterMethod
    public void tearDown() {
        Driver.closeDriver();
    }
}


