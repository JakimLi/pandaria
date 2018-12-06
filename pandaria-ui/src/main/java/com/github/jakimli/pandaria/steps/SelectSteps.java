package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Driver;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SelectSteps {

    @Autowired
    Driver driver;

    @When("^select: '([^\"]*)' value: '([^\"]*)'$")
    public void selectByValue(String selector, String optionValue) {
        driver.select(selector).selectByValue(optionValue);
    }

    @When("^select: '([^\"]*)' index: (\\d+)$")
    public void selectByIndex(String selector, Integer index) {
        driver.select(selector).selectByIndex(index);
    }

    @When("^select: '([^\"]*)' text: '([^\"]*)'$")
    public void selectByText(String selector, String text) {
        driver.select(selector).selectByVisibleText(text);
    }

    @Then("verify: '([^\"]*)' selected value: '([^\"]*)'$")
    public void selectedValue(String selector, String optionValue) {
        WebElement selected = driver.select(selector).getFirstSelectedOption();
        assertThat(selected.getAttribute("value"), is(optionValue));
    }

    @Then("verify: '([^\"]*)' selected text: '([^\"]*)'$")
    public void selectedText(String selector, String text) {
        WebElement selected = driver.select(selector).getFirstSelectedOption();
        assertThat(selected.getText(), is(text));
    }

    @Then("verify: '([^\"]*)' contains items:")
    public void selectContainsItems(String selector, DataTable table) {
        contains(table, driver.select(selector));
    }

    @Then("verify: '([^\"]*)' has items:")
    public void selectHasItems(String selector, DataTable table) {
        Select select = driver.select(selector);

        assertEquals(table.height() - 1, select.getOptions().size());
        contains(table, select);
    }

    private void contains(DataTable table, Select select) {
        table.asMaps().forEach(map -> map.forEach((key, value) -> has(select, key, value)));
    }

    private void has(Select select, String key, String value) {
        assertTrue(select.getOptions().stream().anyMatch(option -> {
            if (key.equals("selected")) {
                return option.isSelected() == value.equals("true");
            }
            return option.getAttribute(key).equals(value);
        }));
    }
}
