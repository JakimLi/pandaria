package com.github.jakimli.pandaria.domain;

import com.github.jakimli.pandaria.configuration.DataSourcesConfiguration;
import com.github.jakimli.pandaria.configuration.DataSourcesConfiguration.DatasourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;

import static com.github.jakimli.pandaria.configuration.DataSourcesConfiguration.DEFAULT;
import static com.google.common.collect.Maps.newHashMap;

@Component
public class DataSources {

    private Map<String, DataSource> dataSources = newHashMap();

    @Autowired
    DataSourcesConfiguration configuration;

    public DataSource dataSource(String name) {
        if (dataSources.containsKey(name)) {
            return dataSources.get(name);
        }

        DataSource connected = connect(name);
        dataSources.put(name, connected);
        return connected;
    }

    private DataSource connect(String name) {
        return configuration.all().stream()
                .filter(property -> property.getName().equalsIgnoreCase(name))
                .findFirst()
                .map(this::buildDataSource)
                .orElseThrow(() -> new RuntimeException("invalid datasource name: " + name));
    }

    private DataSource buildDataSource(DatasourceProperties property) {
        return DataSourceBuilder.create()
                .driverClassName(property.getDriverClassName())
                .url(property.getUrl())
                .username(property.getUsername())
                .password(property.getPassword())
                .build();
    }


    @PostConstruct
    public void validateAdditionalDataSourceName() {
        if (configuration.hasAdditional() && configuration.getAdditional().stream()
                .anyMatch(property -> DEFAULT.equalsIgnoreCase(property.getName()))) {
            throw new RuntimeException(String.format("datasource name: '%s' is reserved", DEFAULT));
        }
    }
}
