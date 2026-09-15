package com.example.hook;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
        driver = new ChromeDriver();
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
