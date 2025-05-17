package utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ReusableMethods {

    // Belirli saniye kadar beklet (sadece zorunlu test senaryolarında)
    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // JS ile tıklama (reklam bloklarını aşmak için)
    public static void clickWithJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    // Elementi JS ile görünür alana kaydır
    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Alert varsa kabul et
    public static void acceptAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            Driver.getDriver().switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("Alert bulunamadı.");
        }
    }

    public static void printElementsText(List<WebElement> elementList) {
        for (WebElement each : elementList) {
            System.out.println(each.getText());
        }
    }

    public static boolean isListNotEmpty(List<WebElement> elementList) {
        return elementList != null && !elementList.isEmpty();
    }

    public static boolean isListEmpty(List<WebElement> elementList) {
        return elementList == null || elementList.isEmpty();
    }
}
