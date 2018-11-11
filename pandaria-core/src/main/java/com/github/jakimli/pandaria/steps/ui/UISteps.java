package com.github.jakimli.pandaria.steps.ui;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.ui.Driver;
import com.github.jakimli.pandaria.domain.variable.Variables;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.jakimli.pandaria.utils.SelectorUtil.by;

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

    @When("^click: '([^\"]*)'$")
    public void click(String selector) {
        driver().findElement(by(selector)).click();
    }

    @When("^navigate back$")
    public void navigateBack() {
        driver().navigate().back();
    }

    @When("^navigate forward$")
    public void navigateForward() {
        driver().navigate().forward();
    }

    @When("^navigate refresh$")
    public void refresh() {
        driver().navigate().refresh();
    }

    private WebDriver driver() {
        return driver.get();
    }
}
