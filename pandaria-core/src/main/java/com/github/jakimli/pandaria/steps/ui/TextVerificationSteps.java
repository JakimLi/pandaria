package com.github.jakimli.pandaria.steps.ui;

import com.github.jakimli.pandaria.domain.ui.Driver;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TextVerificationSteps {

    @Autowired
    Driver driver;

    @Then("verify: '([^\"]*)' text: '([^\"]*)'")
    public void verifyTextById(String id, String text) {
        WebElement element = driver.get().findElement(by(id));
        assertThat(element.getText(), is(text));
    }

    private By by(String selector) {
        if (selector.startsWith("#")) {
            return By.id(selector.substring(1));
        } else if (selector.startsWith(".")) {
            return By.className(selector.substring(1));
        }
        throw new RuntimeException("invalid selector");
    }
}
