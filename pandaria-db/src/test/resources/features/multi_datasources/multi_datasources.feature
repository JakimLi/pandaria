@multi_db
Feature: multi data sources
  We can switch between multiple data sources when working with SQL

  Background:
    * dir: features/multi_datasources

  Scenario: insert and query with specific data source
    * db: foo execute sql:
    """
    DROP TABLE IF EXISTS FOO_USERS;

    CREATE TABLE FOO_USERS(
        NAME VARCHAR(256) NOT NULL,
        AGE INTEGER(5) NOT NULL
    );

    INSERT INTO FOO_USERS(NAME, AGE) VALUES('jakim', 18);
    """

    * db: foo query:
    """
    SELECT NAME, AGE FROM FOO_USERS
    """
    * verify: '$[0].name'='jakim'
    * verify: '$[0].age'=18

    * db: bar execute sql:
    """
    DROP TABLE IF EXISTS BAR_USERS;

    CREATE TABLE BAR_USERS(
        NAME VARCHAR(256) NOT NULL,
        AGE INTEGER(5) NOT NULL
    );

    INSERT INTO BAR_USERS(NAME, AGE) VALUES('jakim', 18);
    """

    * db: bar query:
    """
    SELECT NAME, AGE FROM BAR_USERS
    """
    * verify: '$[0].name'='jakim'
    * verify: '$[0].age'=18

  Scenario: with sql files
    * db: foo execute sql: setup_foo.sql
    * db: foo query: query_foo.sql
    * verify: '$[0].name'='jakim'
    * verify: '$[0].age'=18

    * db: bar execute sql: setup_bar.sql
    * db: bar query: query_bar.sql
    * verify: '$[0].name'='jakim'
    * verify: '$[0].age'=18
