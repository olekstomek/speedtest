package pageObject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import temporaryDataProvider.TemporaryDataProvider;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SetUp {

    protected static WebDriver driver;
    private static WebDriverWait wait;

    protected static SpeedTestPage openHomePage() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(TemporaryDataProvider.mainUrl);
        assertEquals(driver.getCurrentUrl(), TemporaryDataProvider.expectedUrl);
        wait = new WebDriverWait(driver, 30 + SpeedTestPage.MINIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST);

        return new SpeedTestPage(driver);
    }
}
