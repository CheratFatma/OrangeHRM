package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class dashboardPage {
    WebDriver driver ;

    By sections_name = By.cssSelector(".oxd-text.oxd-text--p");
    
    public dashboardPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean showSection(String section){
        if(section.equalsIgnoreCase("My_Actions")){
            return driver.findElements(sections_name).get(1).isDisplayed();
        }
        else {
            return driver.findElements(sections_name).get(2).isDisplayed();
        }
    }
}
