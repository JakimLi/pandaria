package com.github.jakimli.panda.hooks;

import com.github.jakimli.panda.Step;
import cucumber.api.java.Before;

public class SpringContextHook extends Step {

    @Before
    public void load() {
        //this is for spring context to be loaded
        //https://github.com/cucumber/cucumber-jvm/issues/1420
    }
}
