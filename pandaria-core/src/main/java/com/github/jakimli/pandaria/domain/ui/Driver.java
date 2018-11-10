package com.github.jakimli.pandaria.domain.ui;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.google.common.collect.ImmutableMap.of;

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

    interface Supplier {
        WebDriver get();
    }
}
