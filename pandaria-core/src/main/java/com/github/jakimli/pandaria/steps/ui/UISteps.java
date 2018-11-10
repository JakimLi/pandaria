package com.github.jakimli.pandaria.steps.ui;

import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class UISteps {

    private WebDriver driver;

    @When("^open: ([^\"]*)$")
    public void openBrowserAndGo(String uri) {
        ChromeOptions options = new ChromeOptions();

        System.setProperty("webdriver.chrome.driver", "/Work/chromedriver");

        options.setHeadless(false);

        driver = new ChromeDriver(options);

        driver.get(uri);
    }

    @When("^close$")
    public void close() {
       driver.close();
    }
}
