package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.domain.Variables;
import com.github.jakimli.pandaria.domain.database.DatabaseContext;
import com.github.jakimli.pandaria.domain.verification.VerificationContext;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseSteps {

    @Autowired
    DatabaseContext databaseContext;

    @Autowired
    VerificationContext verifier;

    @Autowired
    Variables variables;

    @When("^query:$")
    public void query(String sql) {
        databaseContext.query(variables.interpret(sql));
        verifier.toBeVerified(databaseContext.results());
    }

    @When("^execute sql:$")
    public void executeSql(String sql) {
        databaseContext.execute(variables.interpret(sql));
    }
}
