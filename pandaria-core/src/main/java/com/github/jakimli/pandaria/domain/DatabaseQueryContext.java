package com.github.jakimli.pandaria.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Scope("cucumber-glue")
public class DatabaseQueryContext {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String query;
    private List<Map<String, Object>> results;

    public void query(String query) {
        this.query = query;
    }

    public List<Map<String, Object>> results() {
        return this.results;
    }

    public void send() {
        results = jdbcTemplate.queryForList(query);
    }
}
