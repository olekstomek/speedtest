package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract class PageObject {

    static WebDriver driver;
    static WebDriverWait wait;

    /**
     * Construktor for page objects
     * @param driver
     */
    PageObject(WebDriver driver) {
        int SECONDS = 120;
        PageObject.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, SECONDS);
    }
}