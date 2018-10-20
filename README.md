<a href="https://github.com/jakimli/pandaria">
  <img src="doc/pandaria.png?raw=true" width="100px">
</a>

Pandaria
========
Lightweight API testing tool based on cucumber JVM

[![Build Status](https://travis-ci.org/JakimLi/pandaria.svg?branch=master)](https://travis-ci.org/JakimLi/pandaria)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.jakimli.pandaria/pandaria-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.jakimli.pandaria%22%20AND%20a:%22pandaria-core%22)

Introduction
------------

Pandaria is a DSL written based on cucumber JVM to simplify the HTTP API testing, everything with cucumber still works.
Using pandaria you don't need to learn programming

Example
-------

You can call your api and verify the response
```gherkin
* uri: http://localhost:10080/users/me
* send: GET
* status: 200
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

And you can query database and verify the results

```gherkin
* query:
"""
SELECT NAME, AGE FROM USERS
"""
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```

Or like this:

```gherkin
* query: select.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```
[Mongo DB also supported](doc/usage.md#mongodb-operations)

And you can wait until the verification passed:
```gherkin
* wait: 1000ms times: 3
* uri: /sequence
* send: GET
* response body:
"""
3
"""
```
Above code send GET to `/sequence` and expect response body equals 3, if not it will sleep 1000ms and then retry,
until it succeded passing or exceeds max 3 times and fail. same with database query.

```gherkin
* wait: 1000ms times: 3
* query: select.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```

You can also verify JSON schema:
```gherkin
* uri: /products/1
* send: get
* verify: '$' conform to:
"""
{
"$schema": "http://json-schema.org/draft-07/schema#",
"$id": "http://example.com/product.schema.json",
"title": "Product",
"description": "A product in the catalog",
"type": "object"
}
"""

* verify: '$' conform to: schema/product.schema.json
```

More [Usage](doc/usage.md)

Latest Release
--------------
* 0.2.0

See [Release Notes](doc/release_notes.md)


Get Started
-----------

If you don't need to verify database or mongo, just remove the `pandaria-db` or `pandaria-mongo` from dependency declarations.

### Gradle
```groovy
dependencies {
    testCompile(
            "io.cucumber:cucumber-junit:4.0.0",
            'com.github.jakimli.pandaria:pandaria-core:0.2.0',
            'com.github.jakimli.pandaria:pandaria-db:0.2.0',
            'com.github.jakimli.pandaria:pandaria-mongo:0.2.0'
    )
}
```

### Maven
```xml
<dependencies>
  <dependency>
    <groupId>com.github.jakimli.pandaria</groupId>
    <artifactId>pandaria-core</artifactId>
    <version>0.2.0</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.github.jakimli.pandaria</groupId>
    <artifactId>pandaria-db</artifactId>
    <version>0.2.0</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.github.jakimli.pandaria</groupId>
    <artifactId>pandaria-mongo</artifactId>
    <version>0.2.0</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

If you need to verify database, remember to add specific jdbc driver for your database to `build.gradle` or `pom.xml`,
and add you datasource connection in `application.properties`

application.properties
```
spring.datasource.url=jdbc:mysql://localhost:3307/pandaria?useSSL=false&allowMultiQueries=true
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

If you need to interact with mongo db, also add:
```
mongo.db.name=test
mongo.db.connection=mongodb://root:password@localhost:27017
```

If you're using JUnit, you might want to add below:
```java
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty",
        features = "classpath:features/",
        glue = {"com.github.jakimli.pandaria"})
public class RunCucumberTest {
}
```
**Make sure `com.github.jakimli.pandari` is in the list of cucumber glue.**

Then you can start to write your first automation test.
```gherkin
Feature: hello world
  This is a the first feature for pandaria

  Scenario: hello world
    * uri: https://github.com
    * send: GET
    * status: 200
```
