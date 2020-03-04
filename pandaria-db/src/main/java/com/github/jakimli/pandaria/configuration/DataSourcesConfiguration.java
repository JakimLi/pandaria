package com.github.jakimli.pandaria.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourcesConfiguration {

    public static final String DEFAULT = "default";

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    private List<DatasourceProperties> additional;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public List<DatasourceProperties> getAdditional() {
        return additional;
    }

    public void setAdditional(List<DatasourceProperties> additional) {
        this.additional = additional;
    }

    public List<DatasourceProperties> all() {
        if (!hasAdditional()) {
            return Collections.singletonList(defaultDataSourceProperties());
        }

        return Stream
                .concat(Stream.of(defaultDataSourceProperties()), this.additional.stream())
                .collect(Collectors.toList());
    }

    private DatasourceProperties defaultDataSourceProperties() {
        DatasourceProperties defaultProperties = new DatasourceProperties();
        defaultProperties.setName(DEFAULT);
        defaultProperties.setUrl(this.url);
        defaultProperties.setUsername(this.username);
        defaultProperties.setPassword(this.password);
        defaultProperties.setDriverClassName(this.driverClassName);
        return defaultProperties;
    }

    public boolean hasAdditional() {
        return this.additional != null;
    }

    @Configuration
    @ConfigurationProperties(prefix = "spring.datasource.additional")
    public static class DatasourceProperties {
        private String name;
        private String url;
        private String username;
        private String password;
        private String driverClassName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }
    }
}
