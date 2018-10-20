package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.MongoClient;
import com.github.jakimli.pandaria.domain.MongoQueryContext;
import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.wait.Wait;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.github.jakimli.pandaria.utils.FileUtil.read;

public class MongoSteps {

    @Autowired
    MongoClient mongo;

    @Autowired
    Variables variables;

    @Autowired
    VerificationContext verifier;

    @Autowired
    FeatureConfiguration configuration;

    @Autowired
    MongoQueryContext mongoQuery;

    @Autowired
    Wait wait;

    @When("^collection: '([^\"]*)' insert:$")
    public void insert(String collection, String document) {
        mongo.insert(collection, variables.interpret(document));
    }

    @When("^collection: '([^\"]*)' insert: ([^\"]*)$")
    public void insertFromFile(String collection, String file) throws IOException {
        mongo.insert(collection, variables.interpret(read(configuration.classpathFile(file))));
    }

    @When("^collection: '([^\"]*)' clear$")
    public void clearCollection(String collection) {
        mongo.deleteAll(collection);
    }

    @When("^collection: '([^\"]*)' find all$")
    public void findAll(String collection) {
        mongoQuery.collection(collection);
        verifier.toBeVerified(mongoQuery.findAll());
        wait.waitable(mongoQuery);
    }
}
