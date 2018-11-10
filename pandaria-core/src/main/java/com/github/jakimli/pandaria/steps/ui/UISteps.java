package com.github.jakimli.pandaria.steps.ui;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.ui.Driver;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class UISteps {

    @Autowired
    Driver driver;

    @Autowired
    FeatureConfiguration configuration;

    @Autowired
    Variables variables;

    @When("^open: ([^\"]*)$")
    public void openBrowserAndGo(String uri) {
        driver().get(configuration.uri(variables.interpret(uri)));
    }

    @When("^close$")
    public void close() {
        driver().close();
    }

    private WebDriver driver() {
        return driver.get();
    }
}
