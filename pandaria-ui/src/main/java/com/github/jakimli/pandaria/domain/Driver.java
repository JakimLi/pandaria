package com.github.jakimli.pandaria.domain;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.google.common.collect.ImmutableMap.of;
import static org.openqa.selenium.By.cssSelector;

@Component
@Scope("cucumber-glue")
public class Driver {

    @Value("${browser}")
    public String browser;

    @Autowired
    Chrome chrome;

    private WebDriver driver;

    public WebDriver get() {
        if (driver != null) {
            return driver;
        }

        driver = of("chrome", chrome)
                .get(this.browser)
                .get();

        return driver;
    }

    public WebElement element(String selector) {
        return driver.findElement(cssSelector(selector));
    }

    interface Supplier {
        WebDriver get();
    }
}
