package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.MongoClient;
import com.github.jakimli.pandaria.domain.Variables;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class MongoSteps {

    @Autowired
    MongoClient mongo;

    @Autowired
    Variables variables;

    @When("^collection: '([^\"]*)' insert:$")
    public void insert(String collection, String document) {
        mongo.insert(collection, variables.interpret(document));
    }
}
