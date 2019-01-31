# Internet Speed Test
This is the script that is used to automatically measure your internet connection using speedtest.net

This is a program that uses the `speedtest.net` website to measure Internet quality parameters.
With Selenium, the link speed test is automatically performed and parameter values are retrieved from the website. These parameters are visible in the console during program execution, they are saved to a text file and they are saved to a **text file in an importable format** (eg to Libre Calc). 

The program will indicate the **maximum**, **minimum** and **average** **download**, **upload** and **ping**. It will also save the **time** and **date** of the test, your **ISP (Internet Service Provider)**, the **test server** and the **direct link to the result** of each test.

Screenshots with random names are saved in `speedtest_screenshot/`catalog.


## Configurable parameters:
```
byte NUMBER_OF_TESTS - number of tests

int HOW_MANY_TESTS_TO_SERVERS_SEARCHES - number of tests after which the test server will be searched again

int MAXIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST - maximum time to start the test (seconds)

int MINIMUM_WAITING_TIME_IN_SECONDS_FOR_THE_TEST - minimum time to start the test (seconds)

String RESULTS_FILE_TEXT - file name with the test results

String RESULTS_FILE_TEXT_TO_IMPORT - file name with the test results that you can import into e.g. Libre Office
Calc, Microsoft Excel

String SEPARATING_CHARACTER = ";" - a sign of separating data in a file results_speedtest_to_import.txt (variable RESULTS_FILE_TEXT_TO_IMPORT)

boolean TAKE_SCREENSHOT - true if you want take screenshot or false if you don't want take screenshot

String path - path to the screenshots

String formatPicture - extension for screenshots
```
# Run program / test
## For two automatic tests, the program will provide data such as:
### In your console you see:
> Setup successful.
> Number of tests: 2
>
> Pause for: 12 seconds.
> test number: 1 from 2 tests.
>
> Screenshot made successfully. 
>
>
> Current Date: Cz 2019.01.31 at 11:45:00 PM CET
> Internet service provider (ISP) 
> TOYA
> Server city Lodz
> Server: Virtual Line Sp. z o.o.
> Link to test number 1: https://www.speedtest.net//result/8004041636
> Download for test number 1: 154.08 Mbps
> Upload for test number 1: 25.35 Mbps
> Ping for test number 1: 8 ms
>
>
> Average download result: 
> 1: 154.08 Mbps
> Average upload result: 
> 1: 25.35 Mbps
> Average ping result: 
> 1: 8 ms
>
>
> Search server with the best ping after 1 test.
>
> Pause for: 27 seconds.
> test number: 2 from 2 tests.
>
> Screenshot made successfully. 
>
>
> Current Date: Cz 2019.01.31 at 11:46:13 PM CET
> Internet service provider (ISP) 
> TOYA
> Server city Lodz
> Server: STK TV-SAT 364
> Link to test number 2: https://www.speedtest.net//result/8004043565
> Download for test number 2: 153.2 Mbps
> Upload for test number 2: 25.25 Mbps
> Ping for test number 2: 11 ms
>
>
> Average download result: 
> 2: 153.64 Mbps
> Average upload result: 
> 2: 25.3 Mbps
> Average ping result: 
> 2: 9 ms
>
>
> Success! Test completed! Number of tests: 2.
>
> Saving data to file results_speedtest.txt.
> 
> Data saved to file results_speedtest.txt successful.
>
> End of work.

### In file results_speedtest.txt you see:
> [   
> Current Date: Cz 2019.01.31 at 11:45:00 PM CET   
> Internet service provider (ISP)    
> TOYA   
> Server city Lodz   
> Server: Virtual Line Sp. z o.o.   
> Link to test number 1: https://www.speedtest.net//result/8004041636   
> Download for test number 1: 154.08 Mbps   
> Upload for test number 1: 25.35 Mbps   
> Ping for test number 1: 8 ms   
> 
> 
> Average download result:   
> 1: 154.08 Mbps  
> Average upload result:   
> 1: 25.35 Mbps  
> Average ping result:   
> 1: 8 ms  
>  
> ,    
> Current Date: Cz 2019.01.31 at 11:46:13 PM CET   
> Internet service provider (ISP)   
> TOYA  
> Server city Lodz   
> Server: STK TV-SAT 364   
> Link to test number 2: https://www.speedtest.net//result/8004043565   
> Download for test number 2: 153.2 Mbps   
> Upload for test number 2: 25.25 Mbps   
> Ping for test number 2: 11 ms    
> 
> 
> Average download result:   
> 2: 153.64 Mbps  
> Average upload result:   
> 2: 25.3 Mbps  
> Average ping result:   
> 2: 9 ms  
>  
> ]  
>
> Maximum download result:  
> 154.08  
> Minimum download result:  
> 153.2  
> Maximum upload result:  
> 25.35  
> Minimum upload result:  
> 25.25  
> Maximum ping result:  
> 11.0  
> Minimum ping result:  
> 8.0  
> 
> Average download result:   
> 153.64  
> Average upload result:   
> 25.3  
> Average ping result:   
> 9  
> Statement: Success! Test completed! Number of tests: 2.  

### In file results_speedtest_to_import.txt you see:
> Date and Time;Download speed;Upload speed;Server city ;Server: ;Internet service provider (ISP)  
> Thu Jan 31 23:45:00 CET 2019;154.08;25.35;8;Lodz;Virtual Line Sp. z o.o.;TOYA   
> Thu Jan 31 23:46:13 CET 2019;153.2;25.25;11;Lodz;STK TV-SAT 364;TOYA 

