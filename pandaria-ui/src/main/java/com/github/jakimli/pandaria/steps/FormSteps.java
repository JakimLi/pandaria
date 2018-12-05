package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Driver;
import com.github.jakimli.pandaria.domain.Form;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;

public class FormSteps {

    @Autowired
    Driver driver;

    @When("^submit: '([^\"]*)'$")
    public void submitForm(String selector) {
        driver.element(selector).submit();
    }

    @When("^form: '([^\"]*)'$")
    public void formInput(String locator, DataTable form) {
        new Form(driver.element(locator), form).fill();
    }
}
