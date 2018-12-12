import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Speedtest {
    private WebDriver driver;
    private MainPage mainPage = new MainPage(driver);

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }

    @Before
    public void setupTest() throws InterruptedException {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        mainPage.createMainRowInFile();
    }

    @Test
    public void testSpeedTest() throws InterruptedException {
        mainPage = new MainPage(driver).openSpeedtestWebsite();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("evidon-barrier-acceptbutton"))).click();

        for (int i = 0; i < mainPage.getNUMBER_OF_TESTS(); i++) {
            TimeUnit.SECONDS.sleep(mainPage.setAndReturnSecondsPauseToStartSpeedTest());

            WebElement webElement = driver.findElement(By.className("start-text"));
            Date dateAndTimeNow = new Date();
            webElement.click();
            if (i < 1) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Powrót do wyników"))).click();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.result-item:nth-child(2)")));

            mainPage.getResultsFromPage();
            mainPage.getInformationServerServerCityISP();
            mainPage.getInformationDataUnit();
            mainPage.saveResultFileTextToImport(dateAndTimeNow);
            mainPage.returnMaxAndMinDownloadUploadPing();
            mainPage.getTestIdentyficator();
            mainPage.returnAverageResultDownloadUploadPing();
            mainPage.returnLogsToConsole(simpleDateFormat, i, dateAndTimeNow, mainPage.getDataUnitPing(), mainPage.getDataUnitDownloadAndUpload());

            int HOW_MANY_TESTS_TO_SERVERS_SEARCHES = 2;
            if (mainPage.getCounterTest() == HOW_MANY_TESTS_TO_SERVERS_SEARCHES &&
                    mainPage.getCounterTest() != mainPage.getNUMBER_OF_TESTS()) {
                mainPage.openSpeedtestWebsite();
                System.out.println("Search server with the best ping after " + HOW_MANY_TESTS_TO_SERVERS_SEARCHES + " test.\n");
            }
        }
    }

    @After
    public void teardown() throws InterruptedException {
        if (driver != null) {
            mainPage.getStatementTest();
            mainPage.saveResultFileText();
            System.out.println("End of work.");
            TimeUnit.SECONDS.sleep(2);
            driver.quit();
        }
    }

}