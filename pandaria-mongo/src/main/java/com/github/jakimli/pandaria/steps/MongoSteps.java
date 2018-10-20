package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.MongoClient;
import com.github.jakimli.pandaria.domain.MongoExecutionContext;
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

    @Autowired
    MongoExecutionContext mongoExecution;

    @When("^collection: '([^\"]*)' insert:$")
    public void insert(String collection, String document) {
        mongoExecution.collection(collection);
        mongoExecution.execution(collect -> mongo.insert(collect, variables.interpret(document)));
        mongoExecution.execute();
    }

    @When("^collection: '([^\"]*)' insert: ([^\"]*)$")
    public void insertFromFile(String collection, String file) throws IOException {
        String document = read(configuration.classpathFile(file));
        mongoExecution.collection(collection);
        mongoExecution.execution(collect -> mongo.insert(collect, variables.interpret(document)));
        mongoExecution.execute();
    }

    @When("^collection: '([^\"]*)' clear$")
    public void clearCollection(String collection) {
        mongoExecution.collection(collection);
        mongoExecution.execution(collect -> mongo.deleteAll(collect));
        mongoExecution.execute();
    }

    @When("^collection: '([^\"]*)' find all$")
    public void findAll(String collection) {
        mongoQuery.collection(collection);
        mongoQuery.filter(null);
        verifier.toBeVerified(mongoQuery.find());
        wait.waitable(mongoQuery);
    }

    @When("^collection: '([^\"]*)' find:$")
    public void find(String collection, String filter) {
        mongoQuery.collection(collection);
        mongoQuery.filter(variables.interpret(filter));
        verifier.toBeVerified(mongoQuery.find());
        wait.waitable(mongoQuery);
    }

    @When("^collection: '([^\"]*)' find: ([^\"]*)$")
    public void findFilterFromFile(String collection, String file) throws IOException {
        mongoQuery.collection(collection);
        mongoQuery.filter(variables.interpret(read(configuration.classpathFile(file))));
        verifier.toBeVerified(mongoQuery.find());
        wait.waitable(mongoQuery);
    }
}
