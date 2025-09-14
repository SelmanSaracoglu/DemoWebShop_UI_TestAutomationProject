package com.demo.framework.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class Driver {


    /*
    WebDriver nesnesi static olarak tanımlanmış,
    yani bu sınıf bellekte bir kez yüklendiğinde,
    driver nesnesi tüm proje boyunca tek ve ortak olur.

    Private olduğu için bu değişkene sadece bu sınıf içerisinden erişilebilir.
    Böylece dışarıdan kontrol edilmesi engellenir.

    WebDriver, Selenium kütüphanesinden bir arayüzdür (interface).
    ChromeDriver, FirefoxDriver gibi sınıflar bu interface’i uygular.
     */
    private static WebDriver driver;


    public static WebDriver getDriver(){
        if (driver == null) {
            String browser = ConfigReader.getProperty("browser");

            switch (browser) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                default:
                    driver = new ChromeDriver();
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    public static void closeDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }
}
