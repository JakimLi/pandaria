package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Driver;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class FormSteps {

    @Autowired
    Driver driver;

    @When("^submit: '([^\"]*)'$")
    public void submitForm(String selector) {
        driver.element(selector).submit();
    }
}
