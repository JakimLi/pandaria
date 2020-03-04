package com.github.jakimli.pandaria.domain;

import com.github.jakimli.pandaria.domain.wait.Waitable;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Component
@Scope("cucumber-glue")
public class DatabaseQueryContext implements Waitable<List<Map<String, Object>>> {

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

    @Override
    public void retry() {
        send();
    }

    @Override
    public List<Map<String, Object>> result() {
        return results;
    }

    public void dataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
