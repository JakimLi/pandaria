package com.github.jakimli.panda.steps;

import com.github.jakimli.panda.domain.FeatureConfiguration;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

public class FeatureConfigurationSteps {

    @Autowired
    FeatureConfiguration configuration;

    @Given("^dir: ([^\"]*)")
    public void dir(String dir) {
        configuration.dir(dir);
    }

    @Given("^base uri: ([^\"]*)")
    public void baseUri(String uri) {
        configuration.baseUri(uri);
    }
}
