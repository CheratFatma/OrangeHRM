package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class loginPage {
    WebDriver driver ;

    By username = By.name("username");
    By password = By.name("password");
    By login_btn = By.cssSelector(".orangehrm-login-button");
    
    public loginPage(WebDriver driver){
        this.driver = driver;
    }

    public void fillUsername(String u){
        driver.findElement(username).sendKeys(u);
    }

    public void fillPassword(String p){
        driver.findElement(password).sendKeys(p);
    }

    public void clickBtn(){
        driver.findElement(login_btn).click();
    }

   

}
