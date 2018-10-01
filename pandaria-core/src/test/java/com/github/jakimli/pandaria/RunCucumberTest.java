package com.github.jakimli.pandaria;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty",
        features = "classpath:features/",
        glue = {"com.github.jakimli.pandaria"})
public class RunCucumberTest {
}
