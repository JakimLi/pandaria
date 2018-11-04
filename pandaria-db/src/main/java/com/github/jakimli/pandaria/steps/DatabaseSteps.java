package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.DatabaseExecuteContext;
import com.github.jakimli.pandaria.domain.DatabaseQueryContext;
import com.github.jakimli.pandaria.domain.FeatureConfiguration;
import com.github.jakimli.pandaria.domain.variable.Variables;
import com.github.jakimli.pandaria.domain.VerificationContext;
import com.github.jakimli.pandaria.domain.wait.Wait;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.github.jakimli.pandaria.utils.FileUtil.read;

public class DatabaseSteps {

    @Autowired
    DatabaseQueryContext databaseQueryContext;

    @Autowired
    VerificationContext verifier;

    @Autowired
    Variables variables;

    @Autowired
    FeatureConfiguration configuration;

    @Autowired
    DatabaseExecuteContext databaseExecuteContext;

    @Autowired
    Wait wait;

    @When("^query:$")
    public void query(String sql) {
        databaseQueryContext.query(variables.interpret(sql));
        databaseQueryContext.send();
        verifier.toBeVerified(databaseQueryContext.results());
        wait.waitable(databaseQueryContext);
    }

    @When("^query: ([^\"]*)$")
    public void queryFromFile(String fileName) throws IOException {
        String file = configuration.classpathFile(fileName);
        databaseQueryContext.query(variables.interpret(read(file)));
        databaseQueryContext.send();
        verifier.toBeVerified(databaseQueryContext.results());
        wait.waitable(databaseQueryContext);
    }

    @When("^execute sql:$")
    public void executeSql(String sql) {
        databaseExecuteContext.statement(variables.interpret(sql));
        databaseExecuteContext.execute();
        wait.waitable(databaseExecuteContext);
    }

    @When("^execute sql: ([^\"]*)$")
    public void executeSqlFromFile(String fileName) throws IOException {
        String file = configuration.classpathFile(fileName);
        databaseExecuteContext.statement(variables.interpret(read(file)));
        databaseExecuteContext.execute();
        wait.waitable(databaseExecuteContext);
    }
}
