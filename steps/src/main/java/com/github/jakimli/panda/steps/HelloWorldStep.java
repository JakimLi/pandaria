package com.github.jakimli.panda.steps;

import com.github.jakimli.panda.Step;
import cucumber.api.java.en.Given;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HelloWorldStep extends Step {

    @Given("hello")
    public void hello() {
        assertThat(1, is(1));
    }
}
