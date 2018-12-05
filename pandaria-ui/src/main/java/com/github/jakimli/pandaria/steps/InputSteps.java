package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Driver;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class InputSteps {

    @Autowired
    Driver driver;

    @When("^input: '([^\"]*)' text: '(.*)'$")
    public void input(String selector, String text) {
        driver.element(selector).sendKeys(text);
    }

    @When("^input: '([^\"]*)' clear$")
    public void clear(String selector) {
        driver.element(selector).clear();
    }
}
