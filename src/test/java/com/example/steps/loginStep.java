package com.example.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.WebDriver;

import com.example.hook.baseTest;
import com.example.pages.dashboardPage;
import com.example.pages.loginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class loginStep {
    WebDriver driver = baseTest.driver;
    loginPage lp = baseTest.lp;
    dashboardPage dp = baseTest.dp;

    @Given ("visiter le site {string}")
    public void visitSite(String s){
        driver.get(s);
    }

    @When("saisir le username {string}")
    public void saisir_le_username(String s) {
        lp.fillUsername(s);
    }

    @When("saisir password {string}")
    public void saisir_password(String s) {
        lp.fillPassword(s);
    }

    @When("cliquer sur le bouton login")
    public void cliquer_sur_le_bouton_login() {
        lp.clickBtn();
    }

    @Then("verifier on est sur le dashboard")
    public void verifier_on_est_sur_le_bon_site() {
        assertEquals("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index", driver.getCurrentUrl());
    }

    @Then("la section {string} est visible")
    public void la_section_est_visible(String s) {
        dp.showSection(s);
    }
    
}
