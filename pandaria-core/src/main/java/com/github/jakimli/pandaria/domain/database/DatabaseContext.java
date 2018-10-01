package com.github.jakimli.pandaria.domain.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Scope("cucumber-glue")
public class DatabaseContext {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Map<String, Object>> results;

    public void query(String sql) {
        results = jdbcTemplate.queryForList(sql);
    }

    public void execute(String sql) {
        jdbcTemplate.execute(sql);
    }

    public List<Map<String, Object>> results() {
        return this.results;
    }
}
