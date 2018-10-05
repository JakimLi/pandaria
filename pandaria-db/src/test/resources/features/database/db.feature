@database
Feature: database
  database related operations

  Background:
    * dir: features/database

  Scenario: execute sql and query
    * execute sql:
    """
    DROP TABLE IF EXISTS USERS;

    CREATE TABLE USERS(
        NAME VARCHAR(256) NOT NULL,
        AGE INTEGER(5) NOT NULL
    );

    INSERT INTO USERS(NAME, AGE) VALUES('jakim', 18);
    """

    * query:
    """
    SELECT NAME, AGE FROM USERS
    """
    * verify: '$[0].name'='jakim'
    * verify: '$[0].age'=18

  Scenario: sql from file
    * execute sql: drop_table.sql
    * execute sql: setup.sql
    * query: select.sql
    * verify: '$[0].name'='jakim'
    * verify: '$[0].age'=18
