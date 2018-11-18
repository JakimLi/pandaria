package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Driver;
import com.github.jakimli.pandaria.domain.FeatureConfiguration;
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

    @When("^click: ([^\"]*)$")
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

    @When("^input: ([^\"]*) text: '(.*)'$")
    public void input(String selector, String text) {
        driver.element(selector).sendKeys(text);
    }


    @When("^input: ([^\"]*) clear$")
    public void clear(String selector) {
        driver.element(selector).clear();
    }

    @When("^submit: ([^\"]*)$")
    public void submitForm(String selector) {
        driver.element(selector).submit();
    }

    @When("^select: ([^\"]*) value: '([^\"]*)'$")
    public void selectByValue(String selector, String optionValue) {
        driver.select(selector).selectByValue(optionValue);
    }

    @When("^select: ([^\"]*) index: (\\d+)$")
    public void selectByIndex(String selector, Integer index) {
        driver.select(selector).selectByIndex(index);
    }

    private WebDriver driver() {
        return driver.get();
    }
}
