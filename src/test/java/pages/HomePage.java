package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class HomePage {

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//img[@alt='Tricentis Demo Web Shop']")
    public WebElement homePageLogo;

    @FindBy(linkText = "Register")
    public WebElement registerLink;
    @FindBy(linkText = "Log in")
    public WebElement loginLink;
    @FindBy(linkText = "Log out")
    public WebElement logoutLink;

    @FindBy(linkText = "Shopping cart")
    public WebElement shoppingCartLink;
    @FindBy(linkText = "Wishlist")
    public WebElement wishListLink;


    @FindBy(xpath = "//input[@id='small-searchterms']")
    public WebElement searchBox;
    @FindBy(xpath = "//input[@value='Search']")
    public WebElement searchButton;


    @FindBy(xpath = "//ul[@class='top-menu']//a[not(ancestor::ul[@class='sublist firstLevel'])]")
    public List<WebElement> categories;

    @FindBy(xpath = "//ul[@class='top-menu']//ul[@class='sublist firstLevel']//a")
    public List<WebElement> subCategories;

    /*
    @FindBy(xpath = "//a[contains(text(),'Books')]")
    public WebElement booksCategory;
    @FindBy(xpath = "//a[contains(text(),'Computers')]")
    public WebElement computersCategory;
    @FindBy(xpath = "//a[contains(text(),'Electronics')]")
    public WebElement electronicsCategory;
    @FindBy(xpath = "//a[contains(text(),'Apparel & Shoes')]")
    public WebElement apperalAndShoesCategory;
    @FindBy(xpath = "//a[contains(text(),'Digital downloads')]")
    public WebElement digitalDownloadsCategory;
    @FindBy(xpath = "//a[contains(text(),'Jewelry')]")
    public WebElement jewelryCategory;
    @FindBy(xpath = "//a[contains(text(),'Gift Cards')]")
    public WebElement giftCardsCategory;
     */


    //nextInt(n) metodu,
    //Java’daki Random sınıfına ait bir metottur ve
    // 0 (dahil) ile n (hariç) arasında rastgele bir tamsayı üretir.

    public void clickRandomCategory() {
        Random random = new Random();
        int index = random.nextInt(categories.size());
        categories.get(index).click();
    }



}
