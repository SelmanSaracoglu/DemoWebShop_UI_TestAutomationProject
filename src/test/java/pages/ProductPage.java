package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {
    public ProductPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@id='add-to-cart-button-13']")
    public WebElement AddtoCart;
    @FindBy(xpath = "//p[@class='content']")
    public WebElement AddtoCartSuccessMessage;


    @FindBy(linkText = "Computing and Internet")
    public WebElement computingAndInternetBook;

}
