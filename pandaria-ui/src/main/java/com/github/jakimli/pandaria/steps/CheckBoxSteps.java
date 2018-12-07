package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Driver;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CheckBoxSteps {

    @Autowired
    Driver driver;

    @When("^check: '(.*)'$")
    public void check(String locator) {
        WebElement element = driver.element(locator);
        if (!element.isSelected()) {
            element.click();
        }
    }

    @When("^uncheck: '(.*)'$")
    public void uncheck(String locator) {
        WebElement element = driver.element(locator);
        if (element.isSelected()) {
            element.click();
        }
    }

    @Then("^verify: '(.*)' ((?:un)?checked)$")
    public void verifyChecked(String locator, String checked) {
        assertThat(driver.element(locator).isSelected(), is(checked.equals("checked")));
    }
}
