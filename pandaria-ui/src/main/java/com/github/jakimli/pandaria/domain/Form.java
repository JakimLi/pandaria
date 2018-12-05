package com.github.jakimli.pandaria.domain;

import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Form {
    private final WebElement element;
    private final DataTable table;

    public Form(WebElement element, DataTable table) {
        this.element = element;
        this.table = table;
    }

    public void fill() {
        for (int i = 0; i < table.height(); i++) {
            List<String> row = table.row(i);

            String cssSelector = row.get(0);
            String value = row.get(1);

            this.element
                    .findElement(By.cssSelector(cssSelector))
                    .sendKeys(value);
        }
    }
}
