package com.github.jakimli.pandaria.steps.ui;

import com.github.jakimli.pandaria.domain.ui.Driver;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UIVerificationSteps {

    @Autowired
    Driver driver;

    @Then("verify: ([^\"]*) text: '([^\"]*)'$")
    public void verifyText(String cssSelector, String text) {
        WebElement element = driver.element(cssSelector);
        assertThat(element.getText(), is(text));
    }

    @Then("verify: ([^\"]*) attribute: '([^\"]*)' equals: '([^\"]*)'$")
    public void verifyAttribute(String cssSelector, String attribute, String value) {
        WebElement element = driver.element(cssSelector);
        assertThat(element.getAttribute(attribute), is(value));
    }

    @Then("verify uri: '([^\"]*)'$")
    public void verifyCurrentURI(String uri) {
        assertThat(driver.get().getCurrentUrl(), is(uri));
    }

    @Then("verify: ([^\"]*) selected value: '([^\"]*)'$")
    public void selected(String selector, String optionValue) {
        Select select = new Select(driver.element(selector));
        WebElement selected = select.getFirstSelectedOption();
        assertThat(selected.getAttribute("value"), is(optionValue));
    }

    @Then("verify: ([^\"]*) contains items:")
    public void selectContainsItems(String selector, DataTable table) {
        Select select = new Select(driver.element(selector));
        contains(table, select);
    }

    @Then("verify: ([^\"]*) has items:")
    public void selectHasItems(String selector, DataTable table) {
        Select select = new Select(driver.element(selector));

        assertEquals(table.height() - 1, select.getOptions().size());
        contains(table, select);
    }

    private void contains(DataTable table, Select select) {
        table.asMaps().forEach(map -> map.forEach((key, value) -> has(select, key, value)));
    }

    private void has(Select select, String key, String value) {
        assertTrue(select.getOptions().stream().anyMatch(option -> option.getAttribute(key).equals(value)));
    }
}
