<a href="https://github.com/jakimli/pandaria">
  <img src="doc/pandaria.png?raw=true" width="100px">
</a>

Pandaria
=====
Lightweight API test framework based on cucumber JVM

Introduction
------------

Pandaria is a DSL written based on cucumber JVM to simplify the HTTP API testing, everything with cucumber still works.
Using pandaria you don't need to learn programming

Example
-------

You can call your api and verify the response
```
* uri: http://localhost:10080/users/me
* send: GET
* status: 200
* verify: '$.username'='jakim'
* verify: '$.age'=18
```

And you can query database and verify the results

```
* query:
"""
SELECT NAME, AGE FROM USERS
"""
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```

Or like this:

```
* query: select.sql
* verify: '$[0].name'='jakim'
* verify: '$[0].age'=18
```

More [Usage](doc/usage.md)

Latest Release
--------------
* 0.1.0

See [Release Notes](doc/release_notes.md)


Get Started
-----------

If you don't need to verify database, just remove the `pandaria-db` from dependency declarations.

### Gradle
```groovy
dependencies {
    testCompile(
            "io.cucumber:cucumber-junit:4.0.0",
            'com.github.jakimli.pandaria:pandaria-core:0.1.0',
            'com.github.jakimli.pandaria:pandaria-db:0.1.0'
    )
}
```

### Maven
```xml
<dependencies>
  <dependency>
    <groupId>com.github.jakimli.pandaria</groupId>
    <artifactId>pandaria-core</artifactId>
    <version>0.1.0</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>com.github.jakimli.pandaria</groupId>
    <artifactId>pandaria-db</artifactId>
    <version>0.1.0</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

If you need to verify database, remember to add specific jdbc driver for your database, and add you datasource connection in
`application.properties`

application.properties
```
spring.datasource.url=jdbc:mysql://localhost:3307/pandaria?useSSL=false&allowMultiQueries=true
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

Then you can start to write your first automation test.
```
Feature: hello world
  This is a the first feature for pandaria

  Scenario: hello world
    * uri: https://github.com
    * send: GET
    * status: 200
```
