package com.github.jakimli.pandaria;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {
        "pretty",
        "junit:build/cucumber-reports/cucumber.xml",
        "json:build/cucumber-reports/cucumber.json",
        "html:build/cucumber-reports",
},
        features = "classpath:features/",
        glue = {"com.github.jakimli.pandaria"},
        tags = "not @ignore")
public class RunCucumberTest {
}
