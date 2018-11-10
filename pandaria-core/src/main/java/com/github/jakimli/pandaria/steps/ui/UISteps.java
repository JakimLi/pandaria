package com.github.jakimli.pandaria.steps.ui;

import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class UISteps {
    @When("^open: ([^\"]*)$")
    public void openBrowserAndGo(String uri) {
        ChromeOptions options = new ChromeOptions();

        System.setProperty("webdriver.chrome.driver", "/Work/chromedriver");

        options.setHeadless(false);

        WebDriver driver = new ChromeDriver(options);

        driver.get(uri);
    }
}
