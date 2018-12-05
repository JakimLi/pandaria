package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Driver;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommonVerificationSteps {

    @Autowired
    Driver driver;

    @Then("^verify: '([^\"]*)' text: '([^\"]*)'$")
    public void verifyText(String cssSelector, String text) {
        WebElement element = driver.element(cssSelector);
        assertThat(element.getText(), is(text));
    }

    @Then("^verify: '([^\"]*)' attribute: '([^\"]*)' equals: '([^\"]*)'$")
    public void verifyAttribute(String cssSelector, String attribute, String value) {
        WebElement element = driver.element(cssSelector);
        assertThat(element.getAttribute(attribute), is(value));
    }

    @Then("^verify uri: '([^\"]*)'$")
    public void verifyCurrentURI(String uri) {
        assertThat(driver.get().getCurrentUrl(), is(uri));
    }

    @Then("^verify: '([^\"]*)' (display|hidden)$")
    public void verifyDisplay(String cssSelector, String display) {
        WebElement element = driver.element(cssSelector);
        assertThat(element.isDisplayed(), is(display.equals("display")));
    }

    @Then("^verify: '([^\"]*)' (enabled|disabled)$")
    public void verifyEnabled(String cssSelector, String enabled) {
        WebElement element = driver.element(cssSelector);
        assertThat(element.isEnabled(), is(enabled.equals("enabled")));
    }
}
