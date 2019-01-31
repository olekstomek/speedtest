package Web;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static Test.TestSpeedTestPage.speedTestURL;

public class SpeedTestPage {
    private WebDriver driver;

    /**
     * Main configuration
     * <p>
     * # byte NUMBER_OF_TESTS - number of tests
     * # int HOW_MANY_TESTS_TO_SERVERS_SEARCHES - number of tests after which the test server will be searched again
     * # int MAXIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST - maximum time to start the test (seconds)
     * # int MINIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST - minimum time to start the test (seconds)
     * # String RESULTS_FILE_TEXT - file name with the test results
     * # String RESULTS_FILE_TEXT_TO_IMPORT - file name with the test results that you can import into e.g. Libre Office
     * Calc, Microsoft Excel
     * # String SEPARATING_CHARACTER = ";" - a sign of separating data in a file results_speedtest_to_import.txt
     * (variable RESULTS_FILE_TEXT_TO_IMPORT)
     * # boolean TAKE_SCREENSHOT - true if you want take screenshot or false if you don't want take screenshot
     * # String path - path to the screenshots
     * # String formatPicture - extension for screenshots
     */
    private final byte NUMBER_OF_TESTS = 2;
    public final int HOW_MANY_TESTS_TO_SERVERS_SEARCHES = 2;
    private final int MAXIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST = 30;
    static final int MINIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST = 12;
    private final String RESULTS_FILE_TEXT = "results_speedtest.txt";
    private final String RESULTS_FILE_TEXT_TO_IMPORT = "results_speedtest_to_import.txt";
    private final String SEPARATING_CHARACTER = ";";
    private final boolean TAKE_SCREENSHOT = true;
    private final String path = "speedtest_screenshot/";
    private final String formatPicture = ".PNG";

    private List<String> logs = new ArrayList<>(NUMBER_OF_TESTS);
    private String statement = "Success! Test completed! Number of tests: " + NUMBER_OF_TESTS + ".\n";
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
    private String yourInternetServiceProvider = "";
    private String server = "";
    private String serverCity = "";
    private String identityTest = "";
    private String dataUnitPing = "";
    private String dataUnitDownloadAndUpload = "";
    private int counterTest = 0;

    @FindBy(css = "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > " +
            "div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-connection > " +
            "div > div.pure-u-5-12.u-c.result-item-container-align-left > div > div > div.result-label > a")
    private WebElement serverElementOnPage;

    @FindBy(css = "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > " +
            "div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-connection > " +
            "div > div.pure-u-5-12.u-c.result-item-container-align-left > div > div > div:nth-child(3) > span")
    private WebElement serverCityElementOnPage;

    @FindBy(css = "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > " +
            "div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-connection > " +
            "div > div.pure-u-5-12.u-c.result-item-container-align-right > div > div > div.result-label")
    private WebElement yourInternetServiceProviderElementOnPage;

    @FindBy(css = "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > " +
            "div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-test > div > " +
            "div.result-container.clearfix > div.result-container-speed > " +
            "div.result-item-container.result-item-container-align-right > div > div.result-label > span")
    private WebElement dataUnitPingElementOnPage;

    @FindBy(css = "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > " +
            "div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-test > div > " +
            "div.result-container.clearfix > div.result-container-speed > " +
            "div.result-item-container.result-item-container-align-center > div > div.result-label > span")
    private WebElement dataUnitDownloadAndUploadElementOnPage;

    @FindBy(css = "div.result-item:nth-child(2) > div:nth-child(2) > a:nth-child(1)")
    private WebElement identityTestElementOnPage;

    @FindBy(className = "evidon-barrier-acceptbutton")
    private WebElement acceptButton;

    @FindBy(className = "start-text")
    private WebElement startButton;

    @FindBy(css = ".download-speed")
    private WebElement downloadSpeed;

    @FindBy(css = ".upload-speed")
    private WebElement uploadSpeed;

    @FindBy(css = ".ping-speed")
    private WebElement pingSpeed;

    private WebDriverWait wait;

    public SpeedTestPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 59);
        PageFactory.initElements(driver, this);
    }

    public void operateOnElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        try {
            element.click();
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
    }

    public WebElement getAcceptButton() {
        return acceptButton;
    }

    public WebElement getStartButton() {
        return startButton;
    }

    public WebElement getIdentityTest() {
        return identityTestElementOnPage;
    }

    private String getSpeedTestURL() {
        return speedTestURL;
    }

    public byte getNUMBER_OF_TESTS() {
        return NUMBER_OF_TESTS;
    }

    public void takeScreenshot() {
        if (TAKE_SCREENSHOT) {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(srcFile, new File(path + getCounterTest() + "_test_" + formatPicture));
                System.out.println("Screenshot made successfully. \n");
            } catch (IOException e) {
                System.err.println("Can not save screenshot. \n");
                e.printStackTrace();
            }
        } else {
            System.out.println("Mode without screen shot.");
        }
    }

    public void createMainRowInFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(RESULTS_FILE_TEXT_TO_IMPORT, true))) {
            bufferedWriter
                    .append("Date and Time")
                    .append(SEPARATING_CHARACTER)
                    .append("Download speed")
                    .append(SEPARATING_CHARACTER)
                    .append("Upload speed")
                    .append(SEPARATING_CHARACTER)
                    .append("Server city ")
                    .append(SEPARATING_CHARACTER)
                    .append("Server: ")
                    .append(SEPARATING_CHARACTER)
                    .append("Internet service provider (ISP) \n");
        } catch (IOException e) {
            System.out.println("Error to create file " + RESULTS_FILE_TEXT_TO_IMPORT);
            e.printStackTrace();
        }
        System.out.println("Setup successful.\nNumber of tests: " + NUMBER_OF_TESTS + "\n");
    }

    public void saveResultFileTextToImport(Date dateAndTimeNow) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(RESULTS_FILE_TEXT_TO_IMPORT, true))) {
            bufferedWriter
                    .append(String.valueOf(dateAndTimeNow))
                    .append(SEPARATING_CHARACTER)
                    .append(String.valueOf(download))
                    .append(SEPARATING_CHARACTER)
                    .append(String.valueOf(upload))
                    .append(SEPARATING_CHARACTER)
                    .append(String.valueOf(ping))
                    .append(SEPARATING_CHARACTER)
                    .append(serverCity)
                    .append(SEPARATING_CHARACTER)
                    .append(server)
                    .append(SEPARATING_CHARACTER)
                    .append(yourInternetServiceProvider)
                    .append("\n");
        } catch (IOException e) {
            System.out.println("Error to save data to " + RESULTS_FILE_TEXT_TO_IMPORT);
            e.printStackTrace();
        }
    }

    public void saveResultFileText() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(RESULTS_FILE_TEXT))) {
            bufferedWriter.write(String.valueOf(logs));
            bufferedWriter
                    .append("\nMaximum download result: \n")
                    .append(String.valueOf(getMaxDownload()))
                    .append("\nMinimum download result: \n")
                    .append(String.valueOf(getMinDownload()))
                    .append("\nMaximum upload result: \n")
                    .append(String.valueOf(getMaxUpload()))
                    .append("\nMinimum upload result: \n")
                    .append(String.valueOf(getMinUpload()))
                    .append("\nMaximum ping result: \n")
                    .append(String.valueOf(getMaxPing()))
                    .append("\nMinimum ping result: \n")
                    .append(String.valueOf(getMinPing()))
                    .append("\n\nAverage download result: \n")
                    .append(String.valueOf(getAverageDownload()))
                    .append("\nAverage upload result: \n")
                    .append(String.valueOf(getAverageUpload()))
                    .append("\nAverage ping result: \n")
                    .append(String.valueOf(getAveragePing()))
                    .append("\nStatement: ")
                    .append(statement);
            System.out.println("Data saved to file " + RESULTS_FILE_TEXT + " successful.\n");
        } catch (IOException e) {
            System.out.println("Error to save data " + RESULTS_FILE_TEXT + ".");
            e.printStackTrace();
        }
    }

    public void returnMaxAndMinDownloadUploadPing() {
        maxDownload = Math.max(maxDownload, download);
        maxUpload = Math.max(maxUpload, upload);
        maxPing = Math.max(maxPing, ping);

        minDownload = Math.min(minDownload, download);
        minUpload = Math.min(minUpload, upload);
        minPing = Math.min(minPing, ping);
    }

    public int getCounterTest() {
        return counterTest;
    }

    public void returnAverageResultDownloadUploadPing() {
        sumDownload += download;
        sumUpload += upload;
        sumPing += ping;

        averageDownload = sumDownload / getCounterTest();
        averageUpload = sumUpload / getCounterTest();
        averagePing = sumPing / getCounterTest();
    }

    public void returnLogsToConsole(SimpleDateFormat simpleDateFormat, int i, Date dateAndTimeNow, String dataUnitPing,
                                    String dataUnitDownloadAndUpload) {
        logs.add("\nCurrent Date: " + simpleDateFormat.format(dateAndTimeNow) + "\n" +
                "Internet service provider (ISP) \n" + yourInternetServiceProvider + "\n" +
                "Server city " + serverCity + "\n" +
                "Server: " + server + "\n" +
                "Link to test number " + getCounterTest() + ": " + speedTestURL + "/result/"
                + identityTest + "\n" +
                "Download for test number " + getCounterTest() + ": " + download + " "
                + dataUnitDownloadAndUpload + "\n" +
                "Upload for test number " + getCounterTest() + ": " + upload + " "
                + dataUnitDownloadAndUpload + "\n" +
                "Ping for test number " + getCounterTest() + ": " + ping + " " + dataUnitPing + "\n" +
                "\n\nAverage download result: \n" + getCounterTest() + ": " + averageDownload + " "
                + dataUnitDownloadAndUpload +
                "\nAverage upload result: \n" + getCounterTest() + ": " + averageUpload + " "
                + dataUnitDownloadAndUpload +
                "\nAverage ping result: \n" + getCounterTest() + ": " + averagePing + " "
                + dataUnitPing + "\n\n");
        System.out.println(logs.get(i));
    }

    public void getResultsFromPage() {
        wait.until(ExpectedConditions.visibilityOf(downloadSpeed));
        wait.until(ExpectedConditions.visibilityOf(uploadSpeed));
        wait.until(ExpectedConditions.visibilityOf(pingSpeed));
        try {
            this.download = Float.parseFloat((downloadSpeed).getText());
            this.upload = Float.parseFloat((uploadSpeed).getText());
            this.ping = Integer.parseInt((pingSpeed).getText());
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
    }

    public int setAndReturnSecondsPauseToStartSpeedTest() {
        Random random = new Random();
        final int secondsPauseToStartSpeedTest = random.ints(MINIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST,
                (MAXIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST + 1))
                .findFirst()
                .orElseGet(null);
        ++counterTest;
        System.out.println("Pause for: " + secondsPauseToStartSpeedTest + " seconds.\n" +
                "test number: " + getCounterTest() + " from " + getNUMBER_OF_TESTS() + " tests.\n");
        return secondsPauseToStartSpeedTest;
    }

    public void getInformationServerServerCityISP() {
        wait.until(ExpectedConditions.visibilityOf(serverElementOnPage));
        wait.until(ExpectedConditions.visibilityOf(serverCityElementOnPage));
        wait.until(ExpectedConditions.visibilityOf(yourInternetServiceProviderElementOnPage));
        try {
            this.server = serverElementOnPage.getText();
            this.serverCity = serverCityElementOnPage.getText();
            this.yourInternetServiceProvider = yourInternetServiceProviderElementOnPage.getText();
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
    }

    public void getInformationDataUnit() {
        wait.until(ExpectedConditions.visibilityOf(dataUnitPingElementOnPage));
        wait.until(ExpectedConditions.visibilityOf(dataUnitDownloadAndUploadElementOnPage));
        try {
            this.dataUnitPing = dataUnitPingElementOnPage.getText();
            this.dataUnitDownloadAndUpload = dataUnitDownloadAndUploadElementOnPage.getText();
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
    }

    public String getDataUnitPing() {
        return dataUnitPing;
    }

    public String getDataUnitDownloadAndUpload() {
        return dataUnitDownloadAndUpload;
    }

    public void getTestIdentification() {
        wait.until(ExpectedConditions.visibilityOf(identityTestElementOnPage));
        try {
            this.identityTest = identityTestElementOnPage.getText();
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        }
    }

    public void getStatementTest() {
        if (getCounterTest() < getNUMBER_OF_TESTS()) {
            statement = "WARNING! Incomplete tests: " + (getNUMBER_OF_TESTS() - getCounterTest()) + "!";
            System.err.println(statement);
        } else {
            System.out.println(statement);
        }
        System.out.println("Saving data to file " + RESULTS_FILE_TEXT + ".\n");
    }

    public void refreshSpeedtestWebsite() {
        driver.navigate().to(getSpeedTestURL());
    }

    private float getAverageDownload() {
        return averageDownload;
    }

    private float getMaxDownload() {
        return maxDownload;
    }

    private float getMinDownload() {
        return minDownload;
    }

    private float getAverageUpload() {
        return averageUpload;
    }

    private float getMaxUpload() {
        return maxUpload;
    }

    private float getMinUpload() {
        return minUpload;
    }

    private int getAveragePing() {
        return averagePing;
    }

    private float getMaxPing() {
        return maxPing;
    }

    private float getMinPing() {
        return minPing;
    }

    public void returnToResult(int i) {
        if (i < 1) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Back to test results"))).click();
        }
    }

    public void waitForResults() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.result-item:nth-child(2)")));
    }
}
