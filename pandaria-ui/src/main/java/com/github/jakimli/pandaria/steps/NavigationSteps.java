package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Driver;
import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class NavigationSteps {

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

    @When("^click: '([^\"]*)'$")
    public void click(String selector) {
        driver.element(selector).click();
    }

    @When("^go back$")
    public void navigateBack() {
        driver().navigate().back();
    }

    @When("^go forward$")
    public void navigateForward() {
        driver().navigate().forward();
    }

    @When("^refresh$")
    public void refresh() {
        driver().navigate().refresh();
    }

    private WebDriver driver() {
        return driver.get();
    }
}
