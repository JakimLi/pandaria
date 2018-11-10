package com.github.jakimli.pandaria.domain.ui;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class Chrome implements Driver.Supplier {

    @Value("${chrome.headless}")
    private boolean headless;

    @Value("${chrome.driver}")
    private String driver;

    @Value("${chrome.location")
    private String location;

    @Override
    public WebDriver get() {
        System.setProperty("webdriver.chrome.driver", driver);

        ChromeOptions options = new ChromeOptions();
        options.setHeadless(headless);

        if (StringUtils.isNotEmpty(location)) {
            options.setBinary(location);
        }

        return new ChromeDriver(options);
    }
}
