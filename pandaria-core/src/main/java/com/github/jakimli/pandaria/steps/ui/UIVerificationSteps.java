package com.github.jakimli.pandaria.steps.ui;

import com.github.jakimli.pandaria.domain.ui.Driver;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.By.cssSelector;

public class UIVerificationSteps {

    @Autowired
    Driver driver;

    @Then("verify: ([^\"]*) text: '([^\"]*)'")
    public void verifyText(String cssSelector, String text) {
        WebElement element = driver.get().findElement(cssSelector(cssSelector));
        assertThat(element.getText(), is(text));
    }

    @Then("verify: ([^\"]*) attribute: '([^\"]*)' equals: '([^\"]*)'")
    public void verifyAttribute(String cssSelector, String attribute, String value) {
        WebElement element = driver.get().findElement(cssSelector(cssSelector));
        assertThat(element.getAttribute(attribute), is(value));
    }

    @Then("verify uri: '([^\"]*)'$")
    public void verifyCurrentURI(String uri) {
        assertThat(driver.get().getCurrentUrl(), is(uri));
    }
}
