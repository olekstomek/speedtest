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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Speedtest {
    private WebDriver driver;
    private final String speedTestURL = "http://www.speedtest.net";
    private String identityTest = "";
    private final byte numberOfTest = 2;
    private float download = 0;
    private float averageDownload = 0;
    private float maxDownload = 0;
    private float minDownload = Float.MAX_VALUE;
    private float sumDownload = 0;
    private float upload = 0;
    private float averageUpload = 0;
    private float maxUpload = 0;
    private float minUpload = Float.MAX_VALUE;
    private float sumUpload = 0;
    private int ping = 0;
    private int averagePing = 0;
    private float maxPing = 0;
    private float minPing = Float.MAX_VALUE;
    private int sumPing = 0;
    private int counterTest = 0;
    private final int MAXIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST = 15;
    private final int MINIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST = 7;
    private List<String> logs = new ArrayList<>(numberOfTest);
    private final String RESULTS_FILE_TEXT = "results_speedtest.txt";
    private final String RESULTS_FILE_TEXT_TO_IMPORT = "results_speedtest_to_import.txt";
    private String yourInternetServiceProvider = "";
    private String server = "";
    private String serverCity = "";
    private final String separatingCharacter = ";";
    private final int HOW_MANY_TESTS_TO_SERVERS_SEARCHES = 1;
    private String statement = "Success! Test completed! Number of tests: " + numberOfTest + ".\n";

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
    }

    @Before
    public void setupTest() throws InterruptedException {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(RESULTS_FILE_TEXT_TO_IMPORT, true))) {
            bufferedWriter
                    .append("Date and Time")
                    .append(separatingCharacter)
                    .append("Download speed")
                    .append(separatingCharacter)
                    .append("Upload speed")
                    .append(separatingCharacter)
                    .append("Server city")
                    .append(separatingCharacter)
                    .append("Server")
                    .append(separatingCharacter)
                    .append("Internet service provider (ISP)")
                    .append("\n");
        } catch (IOException e) {
            System.out.println("Error to create file " + RESULTS_FILE_TEXT_TO_IMPORT);
            e.printStackTrace();
        }
        System.out.println("Setup successful!");
        System.out.println("Number od tests: " + numberOfTest);
    }

    @Test
    public void testSpeedTest() throws InterruptedException {
        driver.navigate().to(speedTestURL);
        driver.findElement(By.id("_evidon-banner-acceptbutton")).click();
        Random random = new Random();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        WebDriverWait wait = new WebDriverWait(driver, 60);

        for (int i = 0; i < numberOfTest; i++) {
            int secondsPauseToStartSpeedTest = random.ints(MINIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST, (MAXIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST + 1))
                    .findFirst()
                    .orElseGet(null);
            System.out.println("Pause for: " + secondsPauseToStartSpeedTest + " seconds.\n" +
                    "Test number: " + ++counterTest + " from " + numberOfTest + " tests.\n");
            TimeUnit.SECONDS.sleep(secondsPauseToStartSpeedTest);

            WebElement webElement = driver.findElement(By.className("start-text"));
            Date dateAndTimeNow = new Date();
            webElement.click();
            System.out.println("i : " + i);
            if (i < 1) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Powrót do wyników"))).click();
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.result-item:nth-child(2)")));

            download = Float.parseFloat(driver.findElement(By.cssSelector(".download-speed")).getText());
            upload = Float.parseFloat(driver.findElement(By.cssSelector(".upload-speed")).getText());
            ping = Integer.parseInt(driver.findElement(By.cssSelector(".ping-speed")).getText());
            server = driver
                    .findElement(By.cssSelector(
                            "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-connection > div > div.pure-u-5-12.u-c.result-item-container-align-left > div > div > div.result-label > a"))
                    .getText();
            serverCity = driver
                    .findElement(By.cssSelector(
                            "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-connection > div > div.pure-u-5-12.u-c.result-item-container-align-left > div > div > div:nth-child(3) > span"))
                    .getText();
            yourInternetServiceProvider = driver
                    .findElement(By.cssSelector(
                            "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-connection > div > div.pure-u-5-12.u-c.result-item-container-align-right > div > div > div.result-label"))
                    .getText();
            String dataUnitPing = driver
                    .findElement(By.cssSelector(
                            "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-test > div > div.result-container.clearfix > div.result-container-speed > div.result-item-container.result-item-container-align-right > div > div.result-data > span"))
                    .getText();
            String dataUnitDownloadAndUpload = driver
                    .findElement(By.cssSelector(
                            "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-test > div > div.result-container.clearfix > div.result-container-meta > div > div.result-item.result-item-inline.result-item-align-center.result-item-id > div.result-data > a"))
                    .getText();

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(RESULTS_FILE_TEXT_TO_IMPORT, true))) {
                bufferedWriter
                        .append(String.valueOf(dateAndTimeNow))
                        .append(separatingCharacter)
                        .append(String.valueOf(download))
                        .append(separatingCharacter)
                        .append(String.valueOf(upload))
                        .append(separatingCharacter)
                        .append(String.valueOf(ping))
                        .append(separatingCharacter)
                        .append(serverCity)
                        .append(separatingCharacter)
                        .append(server)
                        .append(separatingCharacter)
                        .append(yourInternetServiceProvider)
                        .append("\n");
            } catch (IOException e) {
                System.out.println("Error to save data to " + RESULTS_FILE_TEXT_TO_IMPORT);
                e.printStackTrace();
            }

            maxDownload = Math.max(maxDownload, download);
            maxUpload = Math.max(maxUpload, upload);
            maxPing = Math.max(maxPing, ping);

            minDownload = Math.min(minDownload, download);
            minUpload = Math.min(minUpload, upload);
            minPing = Math.min(minPing, ping);

            identityTest = driver.findElement(By.cssSelector("div.result-item:nth-child(2) > div:nth-child(2) > a:nth-child(1)")).getText();

            sumDownload += download;
            sumUpload += upload;
            sumPing += ping;

            averageDownload = sumDownload / counterTest;
            averageUpload = sumUpload / counterTest;
            averagePing = sumPing / counterTest;

            logs.add("\nCurrent Date: " + simpleDateFormat.format(dateAndTimeNow) + "\n" +
                    "Internet service provider (ISP): " + yourInternetServiceProvider + "\n" +
                    "Server City: " + serverCity + "\n" +
                    "Server: " + server + "\n" +
                    "Link to test number " + counterTest + ": " + speedTestURL + "/result/" + identityTest + "\n" +
                    "Download for test number " + counterTest + ": " + download + " " + dataUnitDownloadAndUpload + "\n" +
                    "Upload for test number " + counterTest + ": " + upload + " " + dataUnitDownloadAndUpload + "\n" +
                    "Ping for test number " + counterTest + ": " + ping + " " + dataUnitPing + "\n" +
                    "Average download after test number " + counterTest + ": " + averageDownload + " " + dataUnitDownloadAndUpload + "\n" +
                    "Average upload after test number " + counterTest + ": " + averageUpload + " " + dataUnitDownloadAndUpload + "\n" +
                    "Average ping after test number " + counterTest + ": " + averagePing + " " + dataUnitPing + "\n\n");
            System.out.println(logs.get(i));
            if (counterTest == HOW_MANY_TESTS_TO_SERVERS_SEARCHES) {
                driver.navigate().to(speedTestURL);
                System.out.println("Search server with the best ping after " + HOW_MANY_TESTS_TO_SERVERS_SEARCHES + " test.\n");
            }
        }
    }

    @After
    public void teardown() {
        if (driver != null) {
            if (counterTest < numberOfTest) {
                statement = "WARNING! Incomplete tests: " + (numberOfTest - counterTest) + "!";
                System.err.println(statement);
            }
            System.out.println("Saving data to file " + RESULTS_FILE_TEXT + ".\n");
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(RESULTS_FILE_TEXT))) {
                bufferedWriter.write(String.valueOf(logs));
                bufferedWriter
                        .append("\nMaximum download result: ")
                        .append(String.valueOf(maxDownload))
                        .append("\n")
                        .append("Minimum download result: ")
                        .append(String.valueOf(minDownload))
                        .append("\n")
                        .append("Maximum upload result: ")
                        .append(String.valueOf(maxUpload))
                        .append("\n")
                        .append("Minimum upload result: ")
                        .append(String.valueOf(minUpload))
                        .append("\n")
                        .append("Maximum ping result: ")
                        .append(String.valueOf(maxPing))
                        .append("\n")
                        .append("Minimum ping result: ")
                        .append(String.valueOf(minPing))
                        .append("\n\n")
                        .append("Average download result: ")
                        .append(String.valueOf(averageDownload))
                        .append("\n")
                        .append("Average upload result: ")
                        .append(String.valueOf(averageUpload))
                        .append("\n")
                        .append("Average ping result: ")
                        .append(String.valueOf(averagePing))
                        .append("\n").append("Statement: ").append(statement);
                System.out.println("Data saved to file " + RESULTS_FILE_TEXT + " successful.\n");
            } catch (IOException e) {
                System.out.println("Error to save data " + RESULTS_FILE_TEXT + ".");
                e.printStackTrace();
            }
            System.out.println("End of work.");
            driver.quit();
        }
    }
}