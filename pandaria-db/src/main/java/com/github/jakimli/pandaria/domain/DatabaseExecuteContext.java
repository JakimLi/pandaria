package com.github.jakimli.pandaria.domain;

import com.github.jakimli.pandaria.domain.wait.Repeatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class DatabaseExecuteContext extends Repeatable {

    private String statement;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void retry() {
        execute();
    }

    public void execute() {
        jdbcTemplate.execute(statement);
    }

    public void statement(String statement) {
        this.statement = statement;
    }
}
