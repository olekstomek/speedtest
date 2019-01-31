package Test;

import Core.DriverType;
import Core.WebDriverManager;
import Web.SpeedTestPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestSpeedTestPage {

    public static final String speedTestURL = "https://www.speedtest.net/";
    private WebDriverManager driverManager;
    private WebDriver driver;

    @BeforeTest
    public void beforeTest() {
        driverManager = new WebDriverManager(DriverType.CHROME);
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = driverManager.getDriver();
    }

    @AfterMethod
    public void afterMethod() throws InterruptedException {
        System.out.println("End of work.");
        TimeUnit.SECONDS.sleep(2);
        driverManager.quitDriver();
    }

    @Test
    public void testSpeedTest() throws InterruptedException {
        driver.get(speedTestURL);
        assertEquals(driver.getCurrentUrl(), speedTestURL);

        SpeedTestPage speedTestPage = new SpeedTestPage(driver);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        speedTestPage.operateOnElement(speedTestPage.getAcceptButton());
        speedTestPage.createMainRowInFile();
        for (int i = 0; i < speedTestPage.getNUMBER_OF_TESTS(); i++) {
            TimeUnit.SECONDS.sleep(speedTestPage.setAndReturnSecondsPauseToStartSpeedTest());
            speedTestPage.operateOnElement(speedTestPage.getStartButton());
            Date dateAndTimeNow = new Date();

            speedTestPage.returnToResult(i);
            speedTestPage.waitForResults();
            speedTestPage.getResultsFromPage();
            speedTestPage.getInformationServerServerCityISP();
            speedTestPage.getInformationDataUnit();
            speedTestPage.getTestIdentification();
            speedTestPage.takeScreenshot();
            speedTestPage.saveResultFileTextToImport(dateAndTimeNow);
            speedTestPage.returnMaxAndMinDownloadUploadPing();
            speedTestPage.operateOnElement(speedTestPage.getIdentityTest());
            speedTestPage.returnAverageResultDownloadUploadPing();
            speedTestPage.returnLogsToConsole(simpleDateFormat, i, dateAndTimeNow, speedTestPage.getDataUnitPing(),
                    speedTestPage.getDataUnitDownloadAndUpload());

            if (speedTestPage.getCounterTest() == speedTestPage.HOW_MANY_TESTS_TO_SERVERS_SEARCHES &&
                    speedTestPage.getCounterTest() != speedTestPage.getNUMBER_OF_TESTS()) {
                speedTestPage.refreshSpeedtestWebsite();
                System.out.println("Search server with the best ping after " +
                        speedTestPage.HOW_MANY_TESTS_TO_SERVERS_SEARCHES + " test.\n");
            }
        }
        speedTestPage.getStatementTest();
        speedTestPage.saveResultFileText();
    }
}
