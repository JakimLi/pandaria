package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.database.DatabaseContext;
import com.github.jakimli.pandaria.domain.verification.VerificationContext;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.github.jakimli.pandaria.utils.FileUtil.read;

public class DatabaseSteps {

    @Autowired
    DatabaseContext databaseContext;

    @Autowired
    VerificationContext verifier;

    @Autowired
    Variables variables;

    @Autowired
    FeatureConfiguration configuration;

    @When("^query:$")
    public void query(String sql) {
        databaseContext.query(variables.interpret(sql));
        verifier.toBeVerified(databaseContext.results());
    }

    @When("^query: ([^\"]*)$")
    public void queryFromFile(String fileName) throws IOException {
        String file = configuration.classpathFile(fileName);
        databaseContext.query(variables.interpret(read(file)));
        verifier.toBeVerified(databaseContext.results());
    }

    @When("^execute sql:$")
    public void executeSql(String sql) {
        databaseContext.execute(variables.interpret(sql));
    }

    @When("^execute sql: ([^\"]*)$")
    public void executeSqlFromFile(String fileName) throws IOException {
        String file = configuration.classpathFile(fileName);
        databaseContext.execute(variables.interpret(read(file)));
    }
}
