package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
    @FindBy(linkText = "Shopping cart")
    public WebElement shoppingCartLink;
    @FindBy(linkText = "Wishlist")
    public WebElement wishListLink;
    @FindBy(linkText = "Log out") // sadece giriş sonrası görünür
    public WebElement logoutLink;

    @FindBy(xpath = "//input[@id='small-searchterms']")
    public WebElement searchBox;
    @FindBy(xpath = "//input[@value='Search']")
    public WebElement searchButton;


    @FindBy(xpath = "//a[contains(text(),'Books')]")
    public WebElement booksCategory;

}
