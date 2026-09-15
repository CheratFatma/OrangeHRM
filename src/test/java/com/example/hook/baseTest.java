package com.example.hook;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.example.pages.dashboardPage;
import com.example.pages.loginPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class baseTest {
    public static WebDriver driver;
    public static loginPage lp;
    public static dashboardPage dp;


    @Before 
    public void setUp(){
        String nav = System.getProperty("browser", "chrome");

        try {

            URL gridUrl = new URL("http://selenium-hub:4444/wd/hub");

            switch (nav.toLowerCase()) {

                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    driver = new RemoteWebDriver(gridUrl, chromeOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    driver = new RemoteWebDriver(gridUrl, edgeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    driver = new RemoteWebDriver(gridUrl, firefoxOptions);
                    break;

                default:
                    ChromeOptions defaultOptions = new ChromeOptions();
                    driver = new RemoteWebDriver(gridUrl, defaultOptions);
                    break;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
        lp = new loginPage(driver);
        dp = new dashboardPage(driver);
    }

    
    @After 
    public void tearDown(){
        if(driver!=null){
            driver.quit();
        }
        driver = null;
    }
}
