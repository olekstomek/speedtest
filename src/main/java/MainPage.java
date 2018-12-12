import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

class MainPage {
    /**
     * Main configuration
     * <p>
     * byte NUMBER_OF_TESTS - number of tests
     * String RESULTS_FILE_TEXT - file name with the test results
     * String RESULTS_FILE_TEXT_TO_IMPORT - file name with the test results
     * that you can import into e.g. Libre Office Calc, Microsoft Excel
     * String SEPARATING_CHARACTER = ";" - a sign of separating data in a file results_speedtest_to_import.txt
     * (variable RESULTS_FILE_TEXT_TO_IMPORT)
     */
    private final byte NUMBER_OF_TESTS = 3;
    private final String RESULTS_FILE_TEXT = "results_speedtest.txt";
    private final String RESULTS_FILE_TEXT_TO_IMPORT = "results_speedtest_to_import.txt";
    private final String SEPARATING_CHARACTER = ";";

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
    private final String speedTestURL = "http://www.speedtest.net";
    private WebDriver driver;
    private int counterTest = 0;

    MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private String getSpeedTestURL() {
        return speedTestURL;
    }

    byte getNUMBER_OF_TESTS() {
        return NUMBER_OF_TESTS;
    }

    void createMainRowInFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(RESULTS_FILE_TEXT_TO_IMPORT, true))) {
            bufferedWriter
                    .append("Date and Time")
                    .append(SEPARATING_CHARACTER)
                    .append("Download speed")
                    .append(SEPARATING_CHARACTER)
                    .append("Upload speed")
                    .append(SEPARATING_CHARACTER)
                    .append("Server city")
                    .append(SEPARATING_CHARACTER)
                    .append("Server")
                    .append(SEPARATING_CHARACTER)
                    .append("Internet service provider (ISP)")
                    .append("\n");
        } catch (IOException e) {
            System.out.println("Error to create file " + RESULTS_FILE_TEXT_TO_IMPORT);
            e.printStackTrace();
        }
        System.out.println("Setup successful.\nNumber of tests: " + NUMBER_OF_TESTS + "\n");
    }

    void saveResultFileTextToImport(Date dateAndTimeNow) {
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

    void saveResultFileText() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(RESULTS_FILE_TEXT))) {
            bufferedWriter.write(String.valueOf(logs));
            bufferedWriter
                    .append("\nMaximum download result: ")
                    .append(String.valueOf(getMaxDownload()))
                    .append("\n")
                    .append("Minimum download result: ")
                    .append(String.valueOf(getMinDownload()))
                    .append("\n")
                    .append("Maximum upload result: ")
                    .append(String.valueOf(getMaxUpload()))
                    .append("\n")
                    .append("Minimum upload result: ")
                    .append(String.valueOf(getMinUpload()))
                    .append("\n")
                    .append("Maximum ping result: ")
                    .append(String.valueOf(getMaxPing()))
                    .append("\n")
                    .append("Minimum ping result: ")
                    .append(String.valueOf(getMinPing()))
                    .append("\n\n")
                    .append("Average download result: ")
                    .append(String.valueOf(getAverageDownload()))
                    .append("\n")
                    .append("Average upload result: ")
                    .append(String.valueOf(getAverageUpload()))
                    .append("\n")
                    .append("Average ping result: ")
                    .append(String.valueOf(getAveragePing()))
                    .append("\n").append("Statement: ").append(statement);
            System.out.println("Data saved to file " + RESULTS_FILE_TEXT + " successful.\n");
        } catch (IOException e) {
            System.out.println("Error to save data " + RESULTS_FILE_TEXT + ".");
            e.printStackTrace();
        }
    }

    void returnMaxAndMinDownloadUploadPing() {
        maxDownload = Math.max(maxDownload, download);
        maxUpload = Math.max(maxUpload, upload);
        maxPing = Math.max(maxPing, ping);

        minDownload = Math.min(minDownload, download);
        minUpload = Math.min(minUpload, upload);
        minPing = Math.min(minPing, ping);
    }

    int getCounterTest() {
        return counterTest;
    }

    void returnAverageResultDownloadUploadPing() {
        sumDownload += download;
        sumUpload += upload;
        sumPing += ping;

        averageDownload = sumDownload / getCounterTest();
        averageUpload = sumUpload / getCounterTest();
        averagePing = sumPing / getCounterTest();
    }

    void returnLogsToConsole(SimpleDateFormat simpleDateFormat, int i, Date dateAndTimeNow, String dataUnitPing, String dataUnitDownloadAndUpload) {
        logs.add("\nCurrent Date: " + simpleDateFormat.format(dateAndTimeNow) + "\n" +
                "Internet service provider (ISP): " + yourInternetServiceProvider + "\n" +
                "Server City: " + serverCity + "\n" +
                "Server: " + server + "\n" +
                "Link to test number " + getCounterTest() + ": " + speedTestURL + "/result/" + identityTest + "\n" +
                "Download for test number " + getCounterTest() + ": " + download + " " + dataUnitDownloadAndUpload + "\n" +
                "Upload for test number " + getCounterTest() + ": " + upload + " " + dataUnitDownloadAndUpload + "\n" +
                "Ping for test number " + getCounterTest() + ": " + ping + " " + dataUnitPing + "\n" +
                "Average download after test number " + getCounterTest() + ": " + averageDownload + " " + dataUnitDownloadAndUpload + "\n" +
                "Average upload after test number " + getCounterTest() + ": " + averageUpload + " " + dataUnitDownloadAndUpload + "\n" +
                "Average ping after test number " + getCounterTest() + ": " + averagePing + " " + dataUnitPing + "\n\n");
        System.out.println(logs.get(i));
    }

    void getResultsFromPage() {
        this.download = Float.parseFloat(driver.findElement(By.cssSelector(".download-speed")).getText());
        this.upload = Float.parseFloat(driver.findElement(By.cssSelector(".upload-speed")).getText());
        this.ping = Integer.parseInt(driver.findElement(By.cssSelector(".ping-speed")).getText());
    }

    int setAndReturnSecondsPauseToStartSpeedTest() {
        final int MAXIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST = 15;
        final int MINIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST = 7;
        Random random = new Random();
        final int secondsPauseToStartSpeedTest = random.ints(MINIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST,
                (MAXIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST + 1))
                .findFirst()
                .orElseGet(null);
        ++counterTest;
        System.out.println("Pause for: " + secondsPauseToStartSpeedTest + " seconds.\n" +
                "Test number: " + getCounterTest() + " from " + getNUMBER_OF_TESTS() + " tests.\n");
        return secondsPauseToStartSpeedTest;
    }

    void getInformationServerServerCityISP() {
        this.server = driver
                .findElement(By.cssSelector(
                        "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-connection > div > div.pure-u-5-12.u-c.result-item-container-align-left > div > div > div.result-label > a"))
                .getText();
        this.serverCity = driver
                .findElement(By.cssSelector(
                        "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-connection > div > div.pure-u-5-12.u-c.result-item-container-align-left > div > div > div:nth-child(3) > span"))
                .getText();
        this.yourInternetServiceProvider = driver
                .findElement(By.cssSelector(
                        "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-connection > div > div.pure-u-5-12.u-c.result-item-container-align-right > div > div > div.result-label"))
                .getText();
    }

    void getInformationDataUnit() {
        this.dataUnitPing = driver
                .findElement(By.cssSelector(
                        "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-test > div > div.result-container.clearfix > div.result-container-speed > div.result-item-container.result-item-container-align-right > div > div.result-data > span"))
                .getText();
        this.dataUnitDownloadAndUpload = driver
                .findElement(By.cssSelector(
                        "#container > div.main-content > div > div > div > div.pure-u-custom-speedtest > div.speedtest-container.main-row > div.main-view > div > div.result-area.result-area-test > div > div.result-container.clearfix > div.result-container-meta > div > div.result-item.result-item-inline.result-item-align-center.result-item-id > div.result-data > a"))
                .getText();
    }

    String getDataUnitPing() {
        return dataUnitPing;
    }

    String getDataUnitDownloadAndUpload() {
        return dataUnitDownloadAndUpload;
    }

    void getTestIdentyficator() {
        identityTest = driver.findElement(By.cssSelector("div.result-item:nth-child(2) > div:nth-child(2) > a:nth-child(1)")).getText();
    }

    void getStatementTest() {
        if (getCounterTest() < getNUMBER_OF_TESTS()) {
            statement = "WARNING! Incomplete tests: " + (getNUMBER_OF_TESTS() - getCounterTest()) + "!";
            System.err.println(statement);
        } else {
            System.out.println(statement);
        }
        System.out.println("Saving data to file " + RESULTS_FILE_TEXT + ".\n");
    }

    MainPage openSpeedtestWebsite() {
        driver.navigate().to(getSpeedTestURL());
        return this;
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

}