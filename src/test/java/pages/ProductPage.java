package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class ProductPage {
    public ProductPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@value = 'Add to cart']")
    public WebElement AddtoCart;
    @FindBy(xpath = "//div[@class='item-box']//input[@value='Add to cart']")
    public List<WebElement> addToCartButtons;
    @FindBy(xpath = "//p[@class='content']")
    public WebElement addtoCartSuccessMessage;


    @FindBy(xpath = "//div[@class='item-box']")
    public List<WebElement> productList;

    public void clickRandomProduct() {
        Random random = new Random();
        int index = random.nextInt(productList.size());
        productList.get(index).click();
    }


}
