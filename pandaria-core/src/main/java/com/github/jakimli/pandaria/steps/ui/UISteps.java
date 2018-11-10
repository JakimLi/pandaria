package com.github.jakimli.pandaria.steps.ui;

import com.github.jakimli.pandaria.domain.ui.Driver;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class UISteps {

    @Autowired
    Driver driver;

    @When("^open: ([^\"]*)$")
    public void openBrowserAndGo(String uri) {
        driver().get(uri);
    }

    @When("^close$")
    public void close() {
        driver().close();
    }

    private WebDriver driver() {
        return driver.get();
    }
}
