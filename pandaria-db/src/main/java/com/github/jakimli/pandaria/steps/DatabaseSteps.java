package com.github.jakimli.pandaria.steps;

import com.github.jakimli.pandaria.configuration.DataSourcesConfiguration;
import com.github.jakimli.pandaria.domain.*;
import com.github.jakimli.pandaria.domain.expression.Expressions;
import com.github.jakimli.pandaria.domain.wait.Wait;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.github.jakimli.pandaria.configuration.DataSourcesConfiguration.DEFAULT;
import static com.github.jakimli.pandaria.utils.FileUtil.read;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class DatabaseSteps {

    @Autowired
    DatabaseQueryContext databaseQueryContext;

    @Autowired
    VerificationContext verifier;

    @Autowired
    Expressions expressions;

    @Autowired
    FeatureConfiguration configuration;

    @Autowired
    DatabaseExecuteContext databaseExecuteContext;

    @Autowired
    Wait wait;

    @Autowired
    DataSources dataSources;

    @When("^query:$")
    public void query(String sql) {
        databaseQueryContext.dataSource(dataSources.dataSource(DEFAULT));
        databaseQueryContext.query(expressions.evaluate(sql));
        databaseQueryContext.send();
        verifier.toBeVerified(databaseQueryContext.results());
        wait.waitable(databaseQueryContext);
    }

    @When("^query: ([^\"]*)$")
    public void queryFromFile(String fileName) throws IOException {
        String file = configuration.classpathFile(fileName);
        databaseQueryContext.dataSource(dataSources.dataSource(DEFAULT));
        databaseQueryContext.query(expressions.evaluate(read(file)));
        databaseQueryContext.send();
        verifier.toBeVerified(databaseQueryContext.results());
        wait.waitable(databaseQueryContext);
    }

    @When("^db: ([^\" ]*) query:$")
    public void queryByDb(String dbName, String sql) {
        databaseQueryContext.dataSource(dataSources.dataSource(dbName));
        databaseQueryContext.query(expressions.evaluate(sql));
        databaseQueryContext.send();
        verifier.toBeVerified(databaseQueryContext.results());
        wait.waitable(databaseQueryContext);
    }

    @When("^db: ([^\" ]*) query: ([^\"]*)$")
    public void queryFromFileByDb(String dbName, String fileName) throws IOException {
        String file = configuration.classpathFile(fileName);
        databaseQueryContext.dataSource(dataSources.dataSource(dbName));
        databaseQueryContext.query(expressions.evaluate(read(file)));
        databaseQueryContext.send();
        verifier.toBeVerified(databaseQueryContext.results());
        wait.waitable(databaseQueryContext);
    }


    @When("^execute sql:$")
    public void executeSql(String sql) {
        databaseExecuteContext.dataSource(dataSources.dataSource(DEFAULT));
        databaseExecuteContext.statement(expressions.evaluate(sql));
        databaseExecuteContext.execute();
        wait.waitable(databaseExecuteContext);
    }

    @When("^execute sql: ([^\"]*)$")
    public void executeSqlFromFile(String fileName) throws IOException {
        String file = configuration.classpathFile(fileName);
        databaseExecuteContext.dataSource(dataSources.dataSource(DEFAULT));
        databaseExecuteContext.statement(expressions.evaluate(read(file)));
        databaseExecuteContext.execute();
        wait.waitable(databaseExecuteContext);
    }

    @When("^db: ([^\" ]*) execute sql:$")
    public void executeSqlByDb(String dbName, String sql) {
        databaseExecuteContext.statement(expressions.evaluate(sql));
        databaseExecuteContext.dataSource(dataSources.dataSource(dbName));
        databaseExecuteContext.execute();
        wait.waitable(databaseExecuteContext);
    }

    @When("^db: ([^\" ]*) execute sql: ([^\"]*)$")
    public void executeSqlFromFileByDb(String dbName, String fileName) throws IOException {
        String file = configuration.classpathFile(fileName);
        databaseExecuteContext.dataSource(dataSources.dataSource(dbName));
        databaseExecuteContext.statement(expressions.evaluate(read(file)));
        databaseExecuteContext.execute();
        wait.waitable(databaseExecuteContext);
    }
}
