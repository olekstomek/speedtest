import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.SpeedTestPage;
import pageObject.SetUp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Speedtest extends SetUp {

    private static SpeedTestPage speedTestPage;

    @BeforeClass
    public static void setUp() {
        speedTestPage = openHomePage();
    }

    @Test
    public void testSpeedTest() throws InterruptedException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        WebDriverWait wait = new WebDriverWait(driver, 60);
        speedTestPage.operateOnElement(speedTestPage.getAcceptButton());
        speedTestPage.createMainRowInFile();

        for (int i = 0; i < speedTestPage.getNUMBER_OF_TESTS(); i++) {
            TimeUnit.SECONDS.sleep(speedTestPage.setAndReturnSecondsPauseToStartSpeedTest());
            speedTestPage.operateOnElement(speedTestPage.getStartButton());
            Date dateAndTimeNow = new Date();

            speedTestPage.returnToResult(i);
            speedTestPage.waitForResults(wait);
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
    }

    @After
    public void teardown() throws InterruptedException {
        if (driver != null) {
            speedTestPage.getStatementTest();
            speedTestPage.saveResultFileText();
            System.out.println("End of work.");
            TimeUnit.SECONDS.sleep(2);
            driver.quit();
        }
    }

}
