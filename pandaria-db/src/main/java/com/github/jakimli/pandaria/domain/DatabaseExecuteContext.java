package com.github.jakimli.pandaria.domain;

import com.github.jakimli.pandaria.domain.wait.Repeatable;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Scope("cucumber-glue")
public class DatabaseExecuteContext extends Repeatable {

    private String statement;

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

    public void dataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
