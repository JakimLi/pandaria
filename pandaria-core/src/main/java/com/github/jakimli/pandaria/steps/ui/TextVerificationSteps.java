package com.github.jakimli.pandaria.steps.ui;

import com.github.jakimli.pandaria.domain.ui.Driver;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.jakimli.pandaria.utils.SelectorUtil.by;
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
}
